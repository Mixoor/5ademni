package com.mixoor.khademni.payload.response;

import java.time.Instant;

public class ProjectResponse {

    private Long id;

    private String title;

    private String content;

    private UserSummary userSummary;

    private Instant instant;

    private long count;

    public ProjectResponse(Long id, String title, String content, Instant instant, long count, UserSummary userSummary) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.instant = instant;
        this.content = content;
        this.userSummary = userSummary;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
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

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }
}
