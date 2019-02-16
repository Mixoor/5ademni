package com.mixoor.khademni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Review extends DateAudit {


    @EmbeddedId
    private ReviewId id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    @MapsId("clientId")
    private Client client;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer")
    @MapsId("freelancerId")
    private Freelancer freelancer;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String reply;

    @NotBlank
    private int rate;

    public Review(ReviewId id, Client client, Freelancer freelancer, @NotBlank String title, @NotBlank String message, @NotBlank int rate) {
        this.id = id;
        this.client = client;
        this.freelancer = freelancer;
        this.message = message;
        this.title = title;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ReviewId getId() {
        return id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
