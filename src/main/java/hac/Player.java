/*** 
 * The `Player` class represents a player in a quiz game.
 * It tracks the player's current question and score.
 */
package hac;
import java.io.Serializable;


public class Player implements Serializable {

    private int currentQuestion;
    private int currentScore;

    /*** 
     * Constructs a new `Player` object with initial question and score values set to zero.
     */
    public Player() {
        this.currentQuestion = 0;
        this.currentScore = 0;
    }

    /*** 
     * Calculates and returns the player's score based on the current question.
     *
     * @return the player's score
     */
    public double getScore() {
        return currentQuestion * 7.5;
    }

    /*** 
     * Resets the player's current question and score to zero.
     */
    public void reset() {
        this.currentQuestion = 0;
        this.currentScore = 0;
    }

    /*** 
     * Moves the player to the next question by incrementing the current question counter.
     */
    public void nextQuiz() {
        this.currentQuestion += 1;
    }

    /*** 
     * Sets the current question number for the player.
     *
     * @param question the question number to be set
     */
    public void setCurrentQuestion(int question) {
        this.currentQuestion = question;
    }

    /*** 
     * Returns the current question number for the player.
     *
     * @return the current question number
     */
    public int getCurrentQuestion() {
        return this.currentQuestion;
    }

    /*** 
     * Sets the current score for the player.
     *
     * @param score the score to be set
     */
    public void setCurrentScore(int score) {
        this.currentScore = score;
    }

    /*** 
     * Returns the current score for the player.
     *
     * @return the current score
     */
    public int getCurrentScore() {
        return this.currentScore;
    }
}
