
package hac;

import hac.repo.Question;
import hac.repo.QuestionRepository;
import hac.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * The `GameController` class manages the quiz game by handling user authentication,
 * question management, and game progress tracking.
 */
public class GameController implements Serializable {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private InMemoryUserDetailsManager manager;
    @Autowired
    private QuestionRepository repositoryQuestion;
    private Player player;
    private ArrayList<QuizQuestion> questionsGame;

    /***
     * Constructs a `GameController` object with the specified user details manager.
     *
     * @param manager the user details manager
     */
    public GameController(InMemoryUserDetailsManager manager) {
        this.manager = manager;
        player = new Player();
        this.questionsGame = new ArrayList<>();
    }

    /***
     * Checks if the questions in the game are over.
     *
     * @return true if the questions are over, false otherwise
     */
    public boolean questionsIsOver() {
        return player.getCurrentQuestion() >= questionsGame.size();
    }

    /***
     * Returns the current question number as a string.
     *
     * @return the current question number
     */
    public String currentQuestion() {
        return String.valueOf(player.getCurrentQuestion());
    }

    /***
     * Returns the total number of questions as a string.
     *
     * @return the total number of questions
     */
    public String numberOfQuestion() {
        return String.valueOf(questionsGame.size());
    }

    /***
     * Adds a user to the user details manager.
     *
     * @param user the user to be added
     */
    public void addUser(User user) {
        manager.createUser(org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .roles("USER")
                .build());
    }

    /***
     * Adds a question to the game.
     *
     * @param q the question to be added
     */
    public void addQuestion(Question q) {
        ArrayList<String> wrongAnswers = new ArrayList<String>();
        wrongAnswers.add(q.getWrongAnswer1());
        wrongAnswers.add(q.getWrongAnswer2());
        wrongAnswers.add(q.getWrongAnswer3());
        QuizQuestion quizQuestion = new QuizQuestion(q.getQuiz(), q.getCorrectAnswer(), wrongAnswers);
        questionsGame.add(quizQuestion);
    }

    /***
     * Starts the game by resetting the player's progress and loading questions from the repository.
     */
    public void startGame() {
        player.reset();
        questionsGame.clear();
        List<Question> questions = repositoryQuestion.findAll();
        for (Question question : questions) {
            addQuestion(question);
        }
    }

    /***
     * Retrieves the current quiz question from the game.
     *
     * @return the current quiz question
     */
    public String getQuiz() {
        int currentQuestionIndex = player.getCurrentQuestion();
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questionsGame.size()) {
            QuizQuestion currentQuestion = questionsGame.get(currentQuestionIndex);
            return currentQuestion.getQuiz();
        } else {
            return "No quiz available.";
        }
    }

    /***
     * Retrieves the list of answers for the current quiz question.
     *
     * @return the list of answers
     */
    public ArrayList<String> getAnswers() {
        ArrayList<String> wrongAnswers = new ArrayList<String>(questionsGame.get(player.getCurrentQuestion()).getWrongAnswers());
        wrongAnswers.add(questionsGame.get(player.getCurrentQuestion()).getCorrectAnswer());
        return getRandomSortedArrayList(wrongAnswers);
    }

    /***
     * Shuffles and returns a random sorted list of strings.
     *
     * @param list the list to be shuffled and sorted
     * @return the shuffled and sorted list
     */
    private ArrayList<String> getRandomSortedArrayList(ArrayList<String> list) {
        Collections.shuffle(list);
        return list;
    }

    /***
     * Retrieves the correct answer for the current quiz question.
     *
     * @return the correct answer
     */
    public String getCorrectAnswer() {
        return questionsGame.get(player.getCurrentQuestion()).getCorrectAnswer();
    }

    /***
     * Moves the game to the next question.
     */
    public void nextQuestion() {
        player.nextQuiz();
    }

    /***
     * Returns the player's score.
     *
     * @return the player's score
     */
    public double getScore() {
        return player.getScore();
    }
}
