package com.mixoor.khademni.payload.request;

public class ConversationRequest {


    private Long user1;
    private Long user2;


    public ConversationRequest(Long user1, Long user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public Long getUser2() {
        return user2;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
    }
}
