package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "First name is mandatory")
    private String userName;

    @Length(message = "The password needs to be 4-10 chars", min = 4, max = 10)
    private String password;


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {

    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
