package hac.controllers;
import hac.GameController;
import hac.repo.PlayerTable;
import hac.repo.PlayerTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("shared")
public class SharedController {


    @Autowired
    private PlayerTableRepository repositoryTable;

    @Autowired
    @Qualifier("sessionBeanControllerGame")
    private GameController sessionGameController;

    /***
     * The function inits the game and presents the game page
     * @return
     */
    @GetMapping(value = "/start-game")
    public String getGame() {
        sessionGameController.startGame();
        return "redirect:/shared/game";
    }

    /***
     * The function adds the current quiz + all the answer and shows the game page
     * @param model
     * @return
     */
    @GetMapping("/game")
    public String game(Model model) {

        if(!sessionGameController.questionsIsOver()) {
            model.addAttribute("answers", sessionGameController.getAnswers());
            model.addAttribute("question", sessionGameController.getQuiz());
            model.addAttribute("currentQuestion", sessionGameController.currentQuestion());
            model.addAttribute("numberOfQuestions", sessionGameController.numberOfQuestion());
        }
        return "shared/game";
    }

    /***
     *  Submits a question
     * @param selectedAnswer
     * @param model
     * @return
     */
    @PostMapping("/submit-question")
    public String submitQuestion(@RequestParam(name = "selectedAnswer", required = false, defaultValue = "") String selectedAnswer, Model model){

        if (selectedAnswer.equals(sessionGameController.getCorrectAnswer())) {
            sessionGameController.nextQuestion();
            if(sessionGameController.questionsIsOver())
                return "redirect:/shared/score";

            return "redirect:/shared/game";
        }

        return "redirect:/shared/score";
    }

    /***
     * The function checks if the user exists. If the user does
     * not exist, they will be added. If the user already exists and the new score is better than the old score, the function
     * updates the score to the new better score. Finally, the
     * function displays the score page.
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/score")
    public String showScore(Model model, Principal principal) {

        PlayerTable playerTable = repositoryTable.findByUserName(principal.getName());

        if(playerTable == null){
            repositoryTable.save(new PlayerTable(principal.getName(), sessionGameController.getScore()));
        }
        else if(sessionGameController.getScore() > playerTable.getScore()){
            playerTable.setScore(sessionGameController.getScore());
            repositoryTable.save(playerTable);
        }

        model.addAttribute("question", sessionGameController.numberOfQuestion());
        model.addAttribute("answered", sessionGameController.currentQuestion());
        model.addAttribute("score", sessionGameController.getScore());
        return "shared/score";
    }

    /***
     * The function takes all users from the Score DB and presents the top ten users in descending order.
     * @param model
     * @return
     */
    @GetMapping("/score-table")
    public String showTableScore(Model model) {

        Sort sortByHighScore = Sort.by(Sort.Direction.DESC, "score");
        List<PlayerTable> playerTable  = repositoryTable.findAll(sortByHighScore);
        int maxSize = Math.min(playerTable.size(), 10);
        List<PlayerTable> topPlayers = playerTable.subList(0, maxSize);
        model.addAttribute("users", topPlayers);

        return "shared/score-table";
    }
}
