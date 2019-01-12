package com.mixoor.khademni.payload.response;

public class NotificationResponse {

    private Long id;
    private UserSummary sender;

    private String description;
    private String url;

    private int status;


    public NotificationResponse(Long id, UserSummary sender, String description, String url, int status) {
        this.id = id;
        this.sender = sender;
        this.description = description;
        this.url = url;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummary getSender() {
        return sender;
    }

    public void setSender(UserSummary sender) {
        this.sender = sender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
