package com.mixoor.khademni.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
public class Client extends User {


    private float credit = 0.0f;

    private float score = 0.0f;

    public Client() {
        credit = 0.0f;
        score = 0.0f;
    }

    public Client(@NotBlank String name, @NotBlank String aboutMe, @Size(max = 100) String password, @NotBlank @Email @Size(max = 100) String email, String path, @NotBlank @Size(max = 100) String adresse, @NotBlank String city, @NotBlank String country, Date dob, @NotNull Gender gender, @Size(max = 15) String phone_number, Role role, Set<Language> languages) {
        super(name, aboutMe, password, email, path, adresse, city, country, dob, gender, phone_number, role, languages);
        credit = 0.0f;
        score = 0.0f;
    }


    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
