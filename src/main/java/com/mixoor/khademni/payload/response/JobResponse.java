package com.mixoor.khademni.payload.response;

import java.time.Instant;
import java.util.List;

public class JobResponse {

    private Long id;

    private String title;

    private String content;

    private String budget;

    private String delai;

    private boolean availble;

    private UserSummary createdby;

    private Instant created;

    private List<SkillResponse> skills;

    private List<UploadFileResponse> uploadFileResponses;

    public JobResponse() {
    }

    public JobResponse(Long id, String title, String content, String budget, String delai, boolean availble
            , UserSummary createdby, Instant created, List<SkillResponse> skills) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.budget = budget;
        this.delai = delai;
        this.availble = availble;
        this.createdby = createdby;
        this.created = created;
        this.skills = skills;
    }


    public JobResponse(Long id, String title, String content, String budget, String delai, boolean availble, UserSummary createdby, Instant created, List<SkillResponse> skills, List<UploadFileResponse> uploadFileResponses) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.budget = budget;
        this.delai = delai;
        this.availble = availble;
        this.createdby = createdby;
        this.created = created;
        this.skills = skills;
        this.uploadFileResponses = uploadFileResponses;
    }

    public List<UploadFileResponse> getUploadFileResponses() {
        return uploadFileResponses;
    }

    public void setUploadFileResponses(List<UploadFileResponse> uploadFileResponses) {
        this.uploadFileResponses = uploadFileResponses;
    }

    public List<SkillResponse> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillResponse> skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isAvailble() {
        return availble;
    }

    public void setAvailble(boolean availble) {
        this.availble = availble;
    }

    public UserSummary getCreatedby() {
        return createdby;
    }

    public void setCreatedby(UserSummary createdby) {
        this.createdby = createdby;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
