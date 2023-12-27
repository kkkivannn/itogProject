package com.example.individualproject.models;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Reviews")
public class ReviewModel {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Review_Id")
    private long id;
    @Column(name = "Review_Text")
    @NotNull(message = "Text is required")
    private String text;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private UserModel user;

    public ReviewModel() {}

    public ReviewModel(String text, UserModel user) {
        this.text = text;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
