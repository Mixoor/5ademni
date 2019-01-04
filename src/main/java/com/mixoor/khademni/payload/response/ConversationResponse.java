package com.mixoor.khademni.payload.response;

public class ConversationResponse {

    private Long id;
    private UserSummary user1;
    private UserSummary user2;

    private int status;

    public ConversationResponse(Long id, UserSummary user1, UserSummary user2, int status) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummary getUser1() {
        return user1;
    }

    public void setUser1(UserSummary user1) {
        this.user1 = user1;
    }

    public UserSummary getUser2() {
        return user2;
    }

    public void setUser2(UserSummary user2) {
        this.user2 = user2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
