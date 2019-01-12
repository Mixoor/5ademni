package com.mixoor.khademni.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class MessageRequest {

    private Long Sender;
    private Long receiver;
    private String Content;
    private Long conversation;
    private MultipartFile file;

    public MessageRequest(Long sender, Long receiver, String content, Long conversation, MultipartFile file) {
        Sender = sender;
        this.receiver = receiver;
        Content = content;
        this.conversation = conversation;
        this.file = file;
    }


    public Long getSender() {
        return Sender;
    }

    public void setSender(Long sender) {
        Sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getConversation() {
        return conversation;
    }

    public void setConversation(Long conversation) {
        this.conversation = conversation;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
