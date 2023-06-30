package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;


@Entity
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Wrong Answer is mandatory")
    private String wrongAnswers1;
    @NotEmpty(message = "Wrong Answer is mandatory")
    private String wrongAnswers2;
    @NotEmpty(message = "Wrong Answer is mandatory")
    private String wrongAnswers3;

    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Question is mandatory")
    private String question;
    @NotEmpty(message = "Correct Answer is mandatory")
    private String correctAnswer;

    public void setWrongAnswers1() {
        this.wrongAnswers1 = wrongAnswers1;
    }
    public void setWrongAnswers2() {
        this.wrongAnswers2 = wrongAnswers2;
    }
    public void setWrongAnswers3() {
        this.wrongAnswers3 = wrongAnswers3;

    }
    public String getWrongAnswers1() {
        return wrongAnswers1;
    }
    public String getWrongAnswers2() {
        return wrongAnswers2;
    }
    public String getWrongAnswers3() {
        return wrongAnswers3;
    }
    public Question(String email, String question, String correctAnswer,
                            String wrongAnswers1,String wrongAnswers2,String
                            wrongAnswers3) {
        this.email = email;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers1 = wrongAnswers1;
        this.wrongAnswers2 = wrongAnswers2;
        this.wrongAnswers3 = wrongAnswers3;
    }
    public Question() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public String getQuestion() {
        return question;
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

    public void setEmail(String email) {
        this.email=email;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
