package hac.controllers;

import hac.repo.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private QuestionRepository repositoryQuestion;

    @Autowired
    private PlayerTableRepository repositoryTable;


    /***
     * Retrieves the add question page
     * @param question
     * @param model
     * @return
     */
    @GetMapping("/add-question")
    public String getAddQuestion(Question question, Model model)
    {
        model.addAttribute("question", question);
        return "admin/add-question";
    }

    /***
     *  Adds a new question to DB
     * @param question
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/add-question")
    public String addQuestion(@Valid Question question, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("question", question);
            return "admin/add-question";
        }

        repositoryQuestion.save(question);

        return "redirect:/admin/question";
    }

    /***
     * Deletes a question from DB
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        Question question = repositoryQuestion.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryQuestion.delete(question);

        return "redirect:/admin/question";
    }

    /***
     * Deletes a user from the table
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete-user-table")
    public String deleteUserFromTable(@RequestParam("id") long id, Model model) {

        PlayerTable playerTable = repositoryTable.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repositoryTable.delete(playerTable);

        return "redirect:/shared/score-table";
    }

    /***
     * Retrieves the admin question page
     * @param model
     * @return
     */
    @GetMapping("/question")
    public String getQuestion(Model model) {
        model.addAttribute("questions", repositoryQuestion.findAll());
        return "/admin/questions";
    }
}
