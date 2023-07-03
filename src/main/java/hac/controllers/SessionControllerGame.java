package hac.controllers;


import hac.controllers.Beans.ControllerGame;
import hac.controllers.Beans.QuizQuestion;
import hac.repo.Question;
import hac.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class SessionControllerGame {

    @Autowired
    @Qualifier("sessionBeanControllerGame")
    private ControllerGame sessionControllerGame;

    @GetMapping(value = "/shared/start-game")
    public String getGame() {
        sessionControllerGame.startGame();
        return "redirect:/shared/game";
    }


}
