package com.mixoor.khademni.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Review extends DateAudit {


    @EmbeddedId
    private ReviewId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId")
    private User client;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("freelancerId")
    private User freelancer;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String reply;

    @NotBlank
    private int rate;

    public Review(ReviewId id, User client, User freelancer, @NotBlank String title, @NotBlank String message, @NotBlank int rate) {
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

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(User freelancer) {
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
