package hac.controllers.Beans;

import hac.repo.Question;
import hac.repo.QuestionRepository;
import hac.repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControllerGame implements Serializable {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private InMemoryUserDetailsManager manager;
    @Autowired
    private QuestionRepository repositoryQuestion;
    private Player player;
    private ArrayList<QuizQuestion> QuestionsGame;
    public ControllerGame(InMemoryUserDetailsManager manager){
        this.manager = manager;
        player = new Player();
        this.QuestionsGame = new ArrayList<>();
    }

    public String currentQuestion(){
        return String.valueOf(player.getCurrentQuestion());
    }
    public String numberOfQuestion(){
        return String.valueOf(QuestionsGame.size());
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
        QuestionsGame.add(quizQuestion);
    }

    public void startGame() {
        player.reset();
        QuestionsGame.clear();
        List<Question> questions = repositoryQuestion.findAll();
        for (Question question : questions) {
            addQuestion(question);
        }
    }
}
