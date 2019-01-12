package com.mixoor.khademni.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReviewRequest {


    private Long freelancer;

    private Long client;

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    @Max(value = 5)
    @Min(value = 0)
    private int rate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Long getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Long freelancer) {
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
