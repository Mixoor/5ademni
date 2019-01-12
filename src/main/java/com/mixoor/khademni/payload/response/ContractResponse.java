package com.mixoor.khademni.payload.response;

public class ContractResponse {

    private UserSummary client;
    private UserSummary freelancer;


    private String title;
    private String content;

    private Long budget;
    private int delay;


    public ContractResponse(UserSummary client, UserSummary freelancer, String title, String content, Long budget, int delay) {
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

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
