package com.mixoor.khademni.payload.response;

public class NotificationResponse {

     Long id;
     UserSummary userSummary;
     String description;

    public NotificationResponse(Long id, UserSummary userSummary, String description) {
        this.id = id;
        this.userSummary = userSummary;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
