package hac.controllers;

import hac.repo.Question;
import hac.repo.User;
import hac.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository repositoryQuestion;

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        // validate the object and get the errors

        if (result.hasErrors()) {
            return "register";
        }
        repositoryUsers.save(user);
        return "game";
    }
    @PostMapping("admin/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        // we throw an exception if the user is not found
        // since we don't catch the exception here, it will fall back on an error page (see below)
        User user = repositoryUsers
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        repositoryUsers.delete(user);
        model.addAttribute("users", repositoryUsers.findAll());
        return "questions";
    }
    @GetMapping("admin/question")
    public String getQuestion(Question question, Model model) {
        model.addAttribute("question", question);
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
    public String game() {
        return "game";
    }

    @GetMapping("/register")
    public String register(User user, Model model) {
        model.addAttribute("user",user);
        return "register";
    }
}

