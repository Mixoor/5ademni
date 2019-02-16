package com.mixoor.khademni.payload.response;


public class PortfolioResponse {

    private  Long id;

    private String description;

    private String title;

    private String path;

    private UserSummary freelancer;


    public PortfolioResponse(Long id, String description, String title, String path, UserSummary freelancer) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.path = path;
        this.freelancer = freelancer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserSummary getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(UserSummary freelancer) {
        this.freelancer = freelancer;
    }
}
