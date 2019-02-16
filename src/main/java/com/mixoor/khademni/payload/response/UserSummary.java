package com.mixoor.khademni.payload.response;

public class UserSummary {


    private Long id;
    private String name;
    private float vote;
    private String picture;
    private String roleName;

    public UserSummary(Long id, String name, String picture, float vote, String roleName) {
        this.id = id;
        this.name = name;
        this.vote = vote;
        this.picture = picture;
        this.roleName = roleName;
    }
    public UserSummary(Long id, String name, String picture, String roleName) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
