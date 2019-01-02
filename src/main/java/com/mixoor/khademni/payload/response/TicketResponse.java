package com.mixoor.khademni.payload.response;

public class TicketResponse {
    private Long id;
    private String subject;
    private String content;
    private UserSummary userSummary;


    public TicketResponse(Long id, String subject, String content, UserSummary userSummary) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.userSummary = userSummary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }
}
