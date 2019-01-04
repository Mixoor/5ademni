package com.mixoor.khademni.payload.response;

public class MessageResponse {


    // Maybe not need it but if we have the chance to
    // add option of deleting a message send by mistake
    private Long id;

    private UserSummary sender;

    private String content;

    private ConversationResponse conversation;

    private String attachment;

    private  int status;


    public MessageResponse(Long id, UserSummary sender, String content, ConversationResponse conversation, String attachment, int status) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.conversation = conversation;
        this.attachment = attachment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummary getSender() {
        return sender;
    }

    public void setSender(UserSummary sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConversationResponse getConversation() {
        return conversation;
    }

    public void setConversation(ConversationResponse conversation) {
        this.conversation = conversation;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
