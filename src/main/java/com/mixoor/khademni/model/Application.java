package com.mixoor.khademni.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
public class Application {

    @EmbeddedId
    private ApplicationId id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer")
    @MapsId("freelancerId")
    private Freelancer freelancer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job")
    @MapsId("jobId")
    private Job job;

    @Lob
    @NotBlank
    private String content;

    @Column(name = "create_on")
    @Temporal(TemporalType.DATE)
    private Date creationOn = new Date();


    private String budget;

    private String time;

    public Application(ApplicationId applicationId, Freelancer freelancer, Job job, String content, String budget
            , String time) {
        this.budget=budget;
        this.freelancer=freelancer;
        this.id= applicationId;
        this.job=job;
        this.content=content;
        this.budget=budget;
        this.time=time;



    }

    public Application(ApplicationId id, @NotBlank String content, Date creationOn) {
        this.id = id;
        this.content = content;
        this.creationOn = creationOn;
    }

    public ApplicationId getId() {
        return id;
    }

    public void setId(ApplicationId id) {
        this.id = id;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationOn() {
        return creationOn;
    }

    public void setCreationOn(Date creationOn) {
        this.creationOn = creationOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(freelancer, that.freelancer) &&
                Objects.equals(job, that.job) &&
                Objects.equals(content, that.content) &&
                Objects.equals(creationOn, that.creationOn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, freelancer, job, content, creationOn);
    }


    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
