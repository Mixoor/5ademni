package com.mixoor.khademni.payload.request;

public class NotificationRequest {


    private Long receiver;

    private String message;

    //type value varie between [0..3]
    private int type;

    private String url;

    public NotificationRequest(Long receiver, String message, String url, int type) {
        this.receiver = receiver;
        this.url = url;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
