package com.mixoor.khademni.payload.request;

import java.util.Set;

public class FreelancerRequest extends UserRequest {


    private Set<Long> skill;

    public Set<Long> getSkill() {
        return skill;
    }

    public void setSkill(Set<Long> skill) {
        this.skill = skill;
    }

}
