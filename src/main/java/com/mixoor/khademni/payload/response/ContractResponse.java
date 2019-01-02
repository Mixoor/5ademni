package com.mixoor.khademni.payload.response;

public class ContractResponse {

    private UserSummary client;
    private UserSummary freelancer;


    private String title;
    private String content;

    private String budget;
    private String delay;


    public ContractResponse(UserSummary client, UserSummary freelancer, String title, String content, String budget, String delay) {
        this.client = client;
        this.freelancer = freelancer;
        this.title = title;
        this.content = content;
        this.budget = budget;
        this.delay = delay;
    }

    public UserSummary getClient() {
        return client;
    }

    public void setClient(UserSummary client) {
        this.client = client;
    }

    public UserSummary getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserSummary freelancer) {
        this.freelancer = freelancer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
