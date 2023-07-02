package hac.controllers;

import hac.repo.Question;
import hac.repo.QuestionRepository;
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

    @ExceptionHandler({Exception.class})
    public String handleExceptions(Exception e, Model model) {
        model.addAttribute("err", e.getMessage());
        return "error";
    }

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repositoryUsers;
    @Autowired
    private QuestionRepository repositoryQuestion;

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        // validate the object and get the errors

        if (result.hasErrors()) {
            return "register";
        }
        repositoryUsers.save(user);
        return "game";
    }
    @GetMapping("/admin/add-question")
    public String getAddQuestion(Question question, Model model)
    {
        model.addAttribute("question", question);
        return "add-question";
    }
    @PostMapping("/admin/add-question")
    public String addQuestion(@Valid Question question, BindingResult result, Model model) {

//                    model.addAttribute("question", question);
//            model.addAttribute("questions", repositoryQuestion.findAll());
     //   return "redirect:/questions";
        if (result.hasErrors()) {
//            model.addAttribute("question", question);
//            model.addAttribute("questions", repositoryQuestion.findAll());
            model.addAttribute("question", question);
            return "add-question";
        }
//        System.out.println("hhhhhhhhhhhhhhhhhere");
        // validate the object and get the errors
        //repositoryQuestion.save(question);
//        model.addAttribute("question", question);
//        model.addAttribute("questions", repositoryQuestion.findAll());
    //    return "redirect:/questions";

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
    public String game() {
        return "game";
    }

    @GetMapping("/register")
    public String register(User user, Model model) {
        model.addAttribute("user",user);
        return "register";
    }
}

