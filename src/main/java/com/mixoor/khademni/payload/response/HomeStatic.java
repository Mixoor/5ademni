package com.mixoor.khademni.payload.response;

public class HomeStatic {

    Long client;
    Long freelancer;
    Long project;

    public HomeStatic(Long client,Long freelancer,Long project){
        this.client = client;
        this.freelancer=freelancer;
        this.project=project;
    }

    public Long getClient() {
        return client;
    }

    public Long getFreelancer() {
        return freelancer;
    }

    public Long getProject() {
        return project;
    }

    public void setFreelancer(Long freelancer) {
        this.freelancer = freelancer;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public void setProject(Long project) {
        this.project = project;
    }
}
