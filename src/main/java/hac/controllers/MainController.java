package hac.controllers;

import hac.controllers.Beans.ControllerGame;
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
import java.util.Optional;

@Controller
@RequestMapping("")
public class MainController {

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repositoryUsers;
    @Autowired
    private QuestionRepository repositoryQuestion;

    @Autowired
    private PlayerTableRepository repositoryTable;

    @Autowired
    @Qualifier("sessionBeanControllerGame")
    private ControllerGame sessionControllerGame;

    @ExceptionHandler({Exception.class})
    public String handleExceptions(Exception e, Model model) {
        model.addAttribute("err", e.getMessage());
        return "error";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/everyone/register";
        }
        try {
            //if the user doesn't exist it will throw exception
            User existingUser = repositoryUsers.findByUserName(user.getUserName());
            sessionControllerGame.addUser(user);
            repositoryUsers.save(user);
            return "redirect:/everyone/login";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong, try to choose another name");
            return "/everyone/register";
        }
    }

    @GetMapping("/admin/add-question")
    public String getAddQuestion(Question question, Model model)
    {
        model.addAttribute("question", question);
        return "admin/add-question";
    }


    @PostMapping("/admin/add-question")
    public String addQuestion(@Valid Question question, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "admin/add-question";
        }

        repositoryQuestion.save(question);

        return "redirect:/admin/question";
    }
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryQuestion.delete(question);

        return "redirect:/admin/question";
    }
    @PostMapping("/admin/delete-user-table")
    public String deleteUserFromTable(@RequestParam("id") long id, Model model) {

        PlayerTable playerTable = repositoryTable.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryTable.delete(playerTable);

        return "redirect:/shared/score-table";
    }



    @GetMapping("/admin/question")
    public String getQuestion(Model model) {
        model.addAttribute("questions", repositoryQuestion.findAll());
        return "/admin/questions";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "everyone/index";
    }

    @GetMapping("/login")
    public String login() {
        return "everyone/login";
    }

    @GetMapping("/shared/game")
    public String game(Model model) {

        if(!sessionControllerGame.questionsIsOver()) {
            model.addAttribute("answers", sessionControllerGame.getAnswers());
            model.addAttribute("question", sessionControllerGame.getQuiz());
            model.addAttribute("currentQuestion", sessionControllerGame.currentQuestion());
            model.addAttribute("numberOfQuestions", sessionControllerGame.numberOfQuestion());
        }
        return "shared/game";
    }

    @PostMapping("/shared/submit-question")
    public String submitQuestion(@RequestParam(name = "selectedAnswer", required = false, defaultValue = "") String selectedAnswer, Model model){

        System.out.println(selectedAnswer);
        if (selectedAnswer.equals(sessionControllerGame.getCorrectAnswer())) {
            sessionControllerGame.nextQuestion();
            if(sessionControllerGame.questionsIsOver())
                return "redirect:/shared/score";

            return "redirect:/shared/game";
        }

        return "redirect:/shared/score";
    }

    @GetMapping("/shared/score")
    public String showScore(Model model,Principal principal) {

        PlayerTable playerTable = repositoryTable.findByUserName(principal.getName());

        if(playerTable == null){
            repositoryTable.save(new PlayerTable(principal.getName(),sessionControllerGame.getScore()));
        }
        else if(sessionControllerGame.getScore() > playerTable.getScore()){
            repositoryTable.updateScore(principal.getName(), sessionControllerGame.getScore());
        }

        model.addAttribute("question",sessionControllerGame.numberOfQuestion());
        model.addAttribute("answered",sessionControllerGame.currentQuestion());
        model.addAttribute("score",sessionControllerGame.getScore());
        return "shared/score";
    }

    @GetMapping("/shared/score-table")
    public String showTableScore(Model model) {

        Sort sortByHighScore = Sort.by(Sort.Direction.DESC, "score");
        List<PlayerTable> playerTable  = repositoryTable.findAll(sortByHighScore);
        int maxSize = Math.min(playerTable.size(), 10);
        List<PlayerTable> topPlayers = playerTable.subList(0, maxSize);
        model.addAttribute("users", topPlayers);

        return "shared/score-table";
    }
    @GetMapping("/register")
    public String register(User user, Model model) {
        model.addAttribute("user",user);
        return "everyone/register";
    }
}

