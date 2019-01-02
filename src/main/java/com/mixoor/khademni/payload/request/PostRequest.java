package com.mixoor.khademni.payload.request;

import javax.validation.constraints.NotBlank;

public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;


    private String categories;
    private String cover;

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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
