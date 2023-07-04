package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


@Entity
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(message = "The Wrong Answer needs to be 1-100 chars", min = 1, max = 100)
    private String wrongAnswer1;
    @Length(message = "The Wrong Answer needs to be 1-100 chars", min = 1, max = 100)
    @NotEmpty(message = "Wrong Answer is mandatory")
    private String wrongAnswer2;

    @Length(message = "The Wrong Answer needs to be 1-100 chars", min = 1, max = 100)
    @NotEmpty(message = "Wrong Answer is mandatory")
    private String wrongAnswer3;


    @Length(min = 1, max = 100)
    @NotEmpty(message = "Question is mandatory")
    private String quiz;
    @Length(message = "The correctAnswer needs to be 1-100 chars", min = 1, max = 100)
    @NotEmpty(message = "Correct Answer is mandatory")
    private String correctAnswer;

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }
    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }
    public void setWrongAnswer3(String wrongAnswer3) {this.wrongAnswer3 = wrongAnswer3;}
    public String getWrongAnswer1() {
        return wrongAnswer1;
    }
    public String getWrongAnswer2() {
        return wrongAnswer2;
    }
    public String getWrongAnswer3() {
        return wrongAnswer3;
    }
    public Question(String quiz, String correctAnswer,
                            String wrongAnswer1,String wrongAnswer2,String
                            wrongAnswer3) {
        this.quiz = quiz;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }
    public Question() {
    }

    public Long getId() {
        return id;
    }

    public String getQuiz() {
        return quiz;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer=correctAnswer;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }
}
