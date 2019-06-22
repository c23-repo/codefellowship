package com.repocris.wk4.labs.codefellowship;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String body;
    LocalDateTime createdAt;

    // DB relationship annotation
    @ManyToOne
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
