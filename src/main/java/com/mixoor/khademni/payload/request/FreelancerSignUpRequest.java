package com.mixoor.khademni.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class FreelancerSignUpRequest extends ClientSignUpRequest {


    @NotBlank
    private Set<Long> skill;

    public Set<Long> getSkill() {
        return skill;
    }

    public void setSkill(Set<Long> skill) {
        this.skill = skill;
    }


}



