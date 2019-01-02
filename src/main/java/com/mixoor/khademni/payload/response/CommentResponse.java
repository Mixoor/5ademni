package com.mixoor.khademni.payload.response;

import java.time.Instant;

public class CommentResponse {

    private Long id;
    private UserSummary user;
    private String content;
    private Instant createAt;


    public CommentResponse(Long id, UserSummary user, String content, Instant createAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }
}
