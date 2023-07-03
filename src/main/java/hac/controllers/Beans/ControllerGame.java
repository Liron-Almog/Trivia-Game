package hac.controllers.Beans;

import hac.repo.Question;
import hac.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ControllerGame implements Serializable {

    @Autowired
    private QuestionRepository repositoryQuestion;
    private Player player;
    private ArrayList<QuizQuestion> QuestionsGame;
    public ControllerGame(){
        player = new Player();
        this.QuestionsGame = new ArrayList<>();
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
