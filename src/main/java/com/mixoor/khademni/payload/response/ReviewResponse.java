package com.mixoor.khademni.payload.response;

public class ReviewResponse {

    private UserSummary client;
    private String message;
    private int rate;
    private String title;

    public ReviewResponse(UserSummary client, String title,String message, int rate) {
        this.client = client;
        this.message = message;
        this.rate = rate;
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
