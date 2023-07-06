package hac.controllers.Beans;

import hac.repo.Question;
import hac.repo.QuestionRepository;
import hac.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.Serializable;
import java.util.ArrayList;
import hac.controllers.Beans.QuizQuestion;

import java.util.Collections;
import java.util.List;

public class ControllerGame implements Serializable {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private InMemoryUserDetailsManager manager;
    @Autowired
    private QuestionRepository repositoryQuestion;
    private Player player;
    private ArrayList<QuizQuestion> questionsGame;
    public ControllerGame(InMemoryUserDetailsManager manager){
        this.manager = manager;
        player = new Player();
        this.questionsGame = new ArrayList<>();
    }
    public boolean questionsIsOver(){
        return player.getCurrentQuestion() >= questionsGame.size();
    }
    public String currentQuestion(){
        return String.valueOf(player.getCurrentQuestion());
    }
    public String numberOfQuestion(){
        return String.valueOf(questionsGame.size());
    }
    public void addUser(User user){

        manager.createUser(org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .roles("USER")
                .build());
    }
    public void addQuestion(Question q){

        ArrayList<String> wrongAnswers = new ArrayList<String> ();
        wrongAnswers.add(q.getWrongAnswer1());
        wrongAnswers.add(q.getWrongAnswer2());
        wrongAnswers.add(q.getWrongAnswer3());
        QuizQuestion quizQuestion = new QuizQuestion(q.getQuiz(),q.getCorrectAnswer(),wrongAnswers);
        questionsGame.add(quizQuestion);
    }

    public void startGame() {
        player.reset();
        questionsGame.clear();
        List<Question> questions = repositoryQuestion.findAll();
        for (Question question : questions) {
            addQuestion(question);
        }
    }

    public String getQuiz() {

        int currentQuestionIndex = player.getCurrentQuestion();
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questionsGame.size()) {
            QuizQuestion currentQuestion = questionsGame.get(currentQuestionIndex);
            return currentQuestion.getQuiz();
        } else {
            return "No quiz available.";
        }
    }

    public ArrayList<String> getAnswers() {

        ArrayList<String> wrongAnswers = new ArrayList<String>(questionsGame.get(player.getCurrentQuestion()).getWrongAnswers());
        wrongAnswers.add(questionsGame.get(player.getCurrentQuestion()).getCorrectAnswer());

        return getRandomSortedArrayList(wrongAnswers);
    }
    private ArrayList<String> getRandomSortedArrayList(ArrayList<String> list) {
        Collections.shuffle(list);
        return list;
    }

    public String getCorrectAnswer() {

        return questionsGame.get(player.getCurrentQuestion()).getCorrectAnswer();
    }

    public void nextQuestion() {
        player.nextQuiz();

    }

    public double getScore() {
        return player.getScore();
    }
}
