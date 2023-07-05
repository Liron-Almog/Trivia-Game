package hac.repo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class PlayerTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private double score;


    public PlayerTable(String userName, double score) {
        this.userName = userName;
        this.score = score;
    }

    public PlayerTable() {

    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }


    public double getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }


    public void setScore(double score) {
        this.score = score;
    }
}
