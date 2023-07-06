package hac.controllers.Beans;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.lang.String.valueOf;

/**
 * The func is in charge of creating a cart
 */
@Component
public class Player implements Serializable {

    private int currentQuestion;
    private int currentScore;

    public Player() {
        this.currentQuestion = 0;
        this.currentScore = 0;
    }
    public double getScore() {
        return currentQuestion * 7.5;
    }

    public void reset() {
        this.currentQuestion = 0;
        this.currentScore = 0;
    }
    public void nextQuiz() {
        this.currentQuestion+=1;
    }
    // Setter method for currentQuestion
    public void setCurrentQuestion(int question) {
        this.currentQuestion = question;
    }

    // Getter method for currentQuestion
    public int getCurrentQuestion() {
        return this.currentQuestion;
    }

    // Setter method for currentScore
    public void setCurrentScore(int score) {
        this.currentScore = score;
    }

    // Getter method for currentScore
    public int getCurrentScore() {
        return this.currentScore;
    }
}