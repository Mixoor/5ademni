package com.mixoor.khademni.payload.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public class MessageRequest {

    private Long Sender;
    private Long receiver;
    private String Content;
    private Long conversation;

    private MultipartFile file =null;




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
        if(file==null)
            return null;

        return  file;
}

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
