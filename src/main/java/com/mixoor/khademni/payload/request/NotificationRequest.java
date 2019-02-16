package com.mixoor.khademni.payload.request;

public class NotificationRequest {


    private Long receiver;


    //type value varies between [0..8]
    private int type;

    private String url;

    public NotificationRequest(Long receiver ,String url, int type) {
        this.receiver = receiver;
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
