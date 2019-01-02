package com.mixoor.khademni.payload.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class JobRequest {


    private Long userId;
    private String title;
    private String description;
    private String budget;
    private String delai;
    private List<MultipartFile> files;
    private List<String> skills;

    public JobRequest() {
    }

    public JobRequest(Long userId, String title, String description, String budget, String delai, List<MultipartFile> files, List<String> skills) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.delai = delai;
        this.files = files;
        this.skills = skills;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDelai() {
        return delai;
    }

    public void setDelai(String delai) {
        this.delai = delai;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
