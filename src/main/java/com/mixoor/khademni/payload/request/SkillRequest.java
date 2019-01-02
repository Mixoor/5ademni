package com.mixoor.khademni.payload.request;

import javax.validation.constraints.NotBlank;

public class SkillRequest {

    @NotBlank
    private String name;


    public SkillRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
