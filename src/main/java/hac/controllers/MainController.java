package hac.controllers;

import hac.controllers.Beans.ControllerGame;
import hac.repo.Question;
import hac.repo.QuestionRepository;
import hac.repo.User;
import hac.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class MainController {

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repositoryUsers;
    @Autowired
    private QuestionRepository repositoryQuestion;

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
        // validate the object and get the errors

        if (result.hasErrors()) {
            return "register";
        }
        sessionControllerGame.addUser(user);
        repositoryUsers.save(user);
        return "login";
    }
    @GetMapping("/admin/add-question")
    public String getAddQuestion(Question question, Model model)
    {
        model.addAttribute("question", question);
        return "add-question";
    }


    @PostMapping("/admin/add-question")
    public String addQuestion(@Valid Question question, BindingResult result, Model model) {


        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "add-question";
        }


        repositoryQuestion.save(question);

        return "redirect:/admin/question";
    }
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        // we throw an exception if the user is not found
        // since we don't catch the exception here, it will fall back on an error page (see below)
        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryQuestion.delete(question);

        return "redirect:/admin/question";
    }


    @GetMapping("/admin/question")
    public String getQuestion(Model model) {
        model.addAttribute("questions", repositoryQuestion.findAll());
        return "questions";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/shared/game")
    public String game(Model model) {


        model.addAttribute("currentQuestion", sessionControllerGame.currentQuestion());
        model.addAttribute("numberOfQuestions", sessionControllerGame.numberOfQuestion());
        return "game";
    }

    @GetMapping("/register")
    public String register(User user, Model model) {
        model.addAttribute("user",user);
        return "register";
    }
}

