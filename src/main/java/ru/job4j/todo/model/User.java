package ru.job4j.todo.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "User id:" + id + " email:" + email;
    }
}
