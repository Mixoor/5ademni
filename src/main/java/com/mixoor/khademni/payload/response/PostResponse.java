package com.mixoor.khademni.payload.response;

import java.time.Instant;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String category;

    private long commentaires;

    private UserSummary userSummary;

    private Instant instant;

    public PostResponse(Long id, String title, String content, String category, Instant instant, long commentaires, UserSummary userSummary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.instant = instant;
        this.category = category;
        this.commentaires = commentaires;
        this.userSummary = userSummary;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(long commentaires) {
        this.commentaires = commentaires;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }
}
