package com.mixoor.khademni.payload.request;

public class CommentRequest {

    private Long article;
    private Long user;
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(Long article, Long user, String content) {
        this.article = article;
        this.user = user;
        this.content = content;
    }

    public Long getArticle() {
        return article;
    }

    public void setArticle(Long article) {
        this.article = article;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
