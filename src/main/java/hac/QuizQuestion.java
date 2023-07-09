/***
 * The `QuizQuestion` class represents a quiz question with a quiz, correct answer,
 * and a list of wrong answers.
 */
package hac;

import java.util.ArrayList;

public class QuizQuestion {
    private ArrayList<String> wrongAnswers;
    private String quiz;
    private String correctAnswer;

    /***
     * Constructs an empty `QuizQuestion` object.
     */
    public QuizQuestion() {
        wrongAnswers = new ArrayList<>();
    }

    /***
     * Constructs a `QuizQuestion` object with the specified quiz, correct answer, and wrong answers.
     *
     * @param quiz         the quiz question
     * @param correctAnswer the correct answer to the quiz question
     * @param wrongAnswers the list of wrong answers for the quiz question
     */
    public QuizQuestion(String quiz, String correctAnswer, ArrayList<String> wrongAnswers) {
        this.quiz = quiz;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    /***
     * Adds a wrong answer to the list of wrong answers for the quiz question.
     *
     * @param answer the wrong answer to be added
     */
    public void addWrongAnswer(String answer) {
        wrongAnswers.add(answer);
    }

    /***
     * Returns the list of wrong answers for the quiz question.
     *
     * @return the list of wrong answers
     */
    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    /***
     * Sets the quiz question.
     *
     * @param quiz the quiz question to be set
     */
    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    /***
     * Returns the quiz question.
     *
     * @return the quiz question
     */
    public String getQuiz() {
        return quiz;
    }

    /***
     * Sets the correct answer to the quiz question.
     *
     * @param answer the correct answer to be set
     */
    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    /***
     * Returns the correct answer to the quiz question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
