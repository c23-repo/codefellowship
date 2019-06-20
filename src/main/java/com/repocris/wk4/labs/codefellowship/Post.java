package com.repocris.wk4.labs.codefellowship;


import com.sun.tools.javah.Gen;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String body;
    LocalDateTime createdAt;

    // DB relationship annotation
    ApplicationUser creator;


    public Post(){}
    public Post(String body, ApplicationUser creator){

        this.body = body;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ApplicationUser getCreator() {
        return creator;
    }

    public void setCreator(ApplicationUser creator) {
        this.creator = creator;
    }
}
