/*** Package: hac.controllers ***/

package hac.controllers;

import hac.GameController;
import hac.repo.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("")
public class MainController {

    @Autowired
    private UserRepository repositoryUsers;
    @Autowired
    private QuestionRepository repositoryQuestion;

    @Autowired
    private PlayerTableRepository repositoryTable;

    @Autowired
    @Qualifier("sessionBeanControllerGame")
    private GameController sessionGameController;

    /*** Retrieves the game and starts it ***/
    @GetMapping(value = "/shared/start-game")
    public String getGame() {
        sessionGameController.startGame();
        return "redirect:/shared/game";
    }

    /*** Handles exceptions ***/
    @ExceptionHandler({Exception.class})
    public String handleExceptions(Exception e, Model model) {
        model.addAttribute("err", e.getMessage());
        return "error";
    }

    /*** Adds a new user ***/
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/everyone/register";
        }
        try {
            //if the user doesn't exist it will throw an exception
            User existingUser = repositoryUsers.findByUserName(user.getUserName());
            sessionGameController.addUser(user);
            repositoryUsers.save(user);
            return "redirect:/everyone/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong, try to choose another name");
            return "/everyone/register";
        }
    }

    /*** Retrieves the add question page ***/
    @GetMapping("/admin/add-question")
    public String getAddQuestion(Question question, Model model)
    {
        model.addAttribute("question", question);
        return "admin/add-question";
    }

    /*** Adds a new question ***/
    @PostMapping("/admin/add-question")
    public String addQuestion(@Valid Question question, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "admin/add-question";
        }

        repositoryQuestion.save(question);

        return "redirect:/admin/question";
    }

    /*** Deletes a user ***/
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryQuestion.delete(question);

        return "redirect:/admin/question";
    }

    /*** Deletes a user from the table ***/
    @PostMapping("/admin/delete-user-table")
    public String deleteUserFromTable(@RequestParam("id") long id, Model model) {

        PlayerTable playerTable = repositoryTable.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryTable.delete(playerTable);

        return "redirect:/shared/score-table";
    }

    /*** Retrieves the admin question page ***/
    @GetMapping("/admin/question")
    public String getQuestion(Model model) {
        model.addAttribute("questions", repositoryQuestion.findAll());
        return "/admin/questions";
    }

    /*** Retrieves the index page ***/
    @GetMapping("/")
    public String index(Model model) {
        return "everyone/index";
    }

    /*** Retrieves the login page ***/
    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {// User is already logged in
            return "redirect:/shared/game";
        }
        return "everyone/login";
    }

    /*** Retrieves the game page ***/
    @GetMapping("/shared/game")
    public String game(Model model) {

        if(!sessionGameController.questionsIsOver()) {
            model.addAttribute("answers", sessionGameController.getAnswers());
            model.addAttribute("question", sessionGameController.getQuiz());
            model.addAttribute("currentQuestion", sessionGameController.currentQuestion());
            model.addAttribute("numberOfQuestions", sessionGameController.numberOfQuestion());
        }
        return "shared/game";
    }

    /*** Submits a question ***/
    @PostMapping("/shared/submit-question")
    public String submitQuestion(@RequestParam(name = "selectedAnswer", required = false, defaultValue = "") String selectedAnswer, Model model){

        if (selectedAnswer.equals(sessionGameController.getCorrectAnswer())) {
            sessionGameController.nextQuestion();
            if(sessionGameController.questionsIsOver())
                return "redirect:/shared/score";

            return "redirect:/shared/game";
        }

        return "redirect:/shared/score";
    }

    /*** Shows the score page ***/
    @GetMapping("/shared/score")
    public String showScore(Model model,Principal principal) {

        PlayerTable playerTable = repositoryTable.findByUserName(principal.getName());

        if(playerTable == null){
            repositoryTable.save(new PlayerTable(principal.getName(), sessionGameController.getScore()));
        }
        else if(sessionGameController.getScore() > playerTable.getScore()){
            repositoryTable.updateScore(principal.getName(), sessionGameController.getScore());
        }

        model.addAttribute("question", sessionGameController.numberOfQuestion());
        model.addAttribute("answered", sessionGameController.currentQuestion());
        model.addAttribute("score", sessionGameController.getScore());
        return "shared/score";
    }

    /*** Shows the score table page ***/
    @GetMapping("/shared/score-table")
    public String showTableScore(Model model) {

        Sort sortByHighScore = Sort.by(Sort.Direction.DESC, "score");
        List<PlayerTable> playerTable  = repositoryTable.findAll(sortByHighScore);
        int maxSize = Math.min(playerTable.size(), 10);
        List<PlayerTable> topPlayers = playerTable.subList(0, maxSize);
        model.addAttribute("users", topPlayers);

        return "shared/score-table";
    }

    /*** Retrieves the registration page ***/
    @GetMapping("/register")
    public String register(User user, Model model,Principal principal) {

        if (principal != null) {// User is already logged in
            return "redirect:/shared/game";
        }
        model.addAttribute("user",user);
        return "everyone/register";
    }
}
