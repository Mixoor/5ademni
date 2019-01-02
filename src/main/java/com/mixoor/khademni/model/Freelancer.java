package com.mixoor.khademni.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Freelancer extends User {


    private float rating;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            }
    )
    @JoinTable(name = "freelancer_skill",
            joinColumns = {@JoinColumn(name = "freelancer_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}

    )
    Set<Skill> skills = new HashSet<Skill>();
    private Date replyTime;

    @NotBlank
    private float credit = 0;


    public Freelancer() {
    }


    public Freelancer(@NotBlank String name, @NotBlank String aboutMe, @Size(max = 100) String password, @NotBlank @Email @Size(max = 100) String email, String path, @NotBlank @Size(max = 100) String adresse, @NotBlank String city, @NotBlank String country, Date dob, @NotNull Gender gender, @Size(max = 15) String phone_number, Role role, Set<Language> languages, Date replyTime, @NotBlank float credit, float rating, Set<Skill> skills) {
        super(name, aboutMe, password, email, path, adresse, city, country, dob, gender, phone_number, role, languages);
        this.replyTime = replyTime;
        this.credit = credit;
        this.rating = rating;
        this.skills = skills;
    }


    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        getSkills().add(skill);
        skill.getFreelancers().add(this);
    }

    public void removeSkill(Skill skill) {
        getSkills().remove(skill);
        skill.getFreelancers().remove(this);
    }


}
