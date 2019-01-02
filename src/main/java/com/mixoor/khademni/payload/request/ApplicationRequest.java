package com.mixoor.khademni.payload.request;

import javax.validation.constraints.NotBlank;

public class ApplicationRequest {


    @NotBlank
    private Long freelancerId;

    @NotBlank
    private Long jobId;

    @NotBlank
    private String content;

    @NotBlank
    private String time;

    @NotBlank
    private String budget;

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
