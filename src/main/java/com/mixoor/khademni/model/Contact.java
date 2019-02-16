package com.mixoor.khademni.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Contact extends DateAudit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String subject;


    @NotBlank
    private String email;



    @NotBlank
    @Lob
    private String content;

    public Contact(){}
    public Contact(@NotBlank String subject, @NotBlank String email, @NotBlank String content) {
        this.subject = subject;
        this.email = email;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
