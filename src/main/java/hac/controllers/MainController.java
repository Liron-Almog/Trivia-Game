package hac.controllers;

import hac.repo.User;
import hac.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repository;

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        // validate the object and get the errors
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        if (result.hasErrors()) {
            return "register";
        }
        repository.save(user);
        return "game";
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
    public String register() {
        return "register";
    }
}

