package hac.controllers.Beans;

import java.util.ArrayList;

public class QuizQuestion {
    private ArrayList<String> wrongAnswers;
    // Add a wrong answer to the wrongAnswers list
    public void addWrongAnswer(String answer) {
        wrongAnswers.add(answer);
    }
    public QuizQuestion(){}
    public QuizQuestion(String quiz, String correctAnswer, ArrayList<String> wrongAnswers) {
        this.quiz = quiz;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }
    private String quiz;
    private String correctAnswer;


    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }
    // Setter methods for quiz
    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    // Getter methods for quiz
    public String getQuiz() {
        return this.quiz;
    }

    // Setter methods for correctAnswer
    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    // Getter methods for correctAnswer
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}
