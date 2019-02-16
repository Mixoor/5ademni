package com.mixoor.khademni.payload.response;

import com.mixoor.khademni.service.UserService;

public class ReviewResponse {

    private UserSummary client;
    private UserSummary freelancer;
    private String message;
    private int rate;
    private String replay;
    private String title;

    public ReviewResponse(UserSummary client,UserSummary freelancer ,String title, String message, int rate,String replay) {
        this.client = client;
        this.freelancer=freelancer;
        this.message = message;
        this.rate = rate;
        this.replay = replay;
    }

    public UserSummary getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserSummary freelancer) {
        this.freelancer = freelancer;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserSummary getClient() {
        return client;
    }

    public void setClient(UserSummary client) {
        this.client = client;
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
