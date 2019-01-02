package com.mixoor.khademni.payload.response;


public class ApplicationResponse {

    private Long job;

    private Long application;

    private UserSummary userSummary;

    private String content;

    private String budget;

    private String time;

    public ApplicationResponse() {
    }

    public ApplicationResponse(Long job, Long application, UserSummary userSummary, String content, String budget, String time) {
        this.job = job;
        this.application = application;
        this.userSummary = userSummary;
        this.content = content;
        this.budget = budget;
        this.time = time;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }

    public Long getJob() {
        return job;
    }

    public void setJob(Long job) {
        this.job = job;
    }

    public Long getApplication() {
        return application;
    }

    public void setApplication(Long application) {
        this.application = application;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
