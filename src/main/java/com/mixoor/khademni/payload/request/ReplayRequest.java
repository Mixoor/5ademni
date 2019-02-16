package com.mixoor.khademni.payload.request;

public class ReplayRequest {


    Long client;

    String replay;

    public Long getFreelancer() {
        return client;
    }

    public void setFreelancer(Long freelancer) {
        this.client = freelancer;
    }

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }
}
