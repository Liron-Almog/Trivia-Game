
package hac.controllers;
import hac.GameController;
import hac.repo.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("")
public class MainController {

    @Autowired
    private UserRepository repositoryUsers;


    @Autowired
    @Qualifier("sessionBeanControllerGame")
    private GameController sessionGameController;


    /***
     * Handles exceptions
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler({Exception.class})
    public String handleExceptions(Exception e, Model model) {
        model.addAttribute("err", e.getMessage());
        return "error";
    }

    /***
     *The function adds users and inserts the user into the database.
     * If the name of the user already exists, an exception will be thrown(register page will be presented).
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/everyone/register";
        }
        try {
            repositoryUsers.save(user);
            sessionGameController.addUser(user);
            return "redirect:/everyone/login";

        } catch (Exception e ) {
            model.addAttribute("errorMessage", "Something went wrong, try to choose another name");
            return "/everyone/register";
        }
    }

    /***
     The function checks if the user is already connected.
     If the user is not connected, the function returns the index page.
     * @param principal
     * @return
     */
    @GetMapping("/")
    public String index(Principal principal) {

        if (principal != null) {// User is already logged in
            return "redirect:/shared/game";
        }
        return "everyone/index";
    }

    /***
     The function checks if the user is already connected.
     If the user is not connected, the function returns the login page.
     * @param principal
     * @return
     */
    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {// User is already logged in
            return "redirect:/shared/game";
        }
        return "everyone/login";
    }

    /***
     * Retrieves the registration page
     * @param user
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/register")
    public String register(User user, Model model,Principal principal) {

        if (principal != null) {
            return "redirect:/shared/game";
        }
        model.addAttribute("user",user);
        return "everyone/register";
    }
}
