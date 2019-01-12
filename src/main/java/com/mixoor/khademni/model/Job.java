package com.mixoor.khademni.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Job extends DateAudit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private Long budget;
    @NotBlank
    private int  delai;

    private boolean availble = true;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id")
    private Freelancer freelancer;
    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private Set<Document> files = new HashSet<Document>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    private Set<Application> applications = new HashSet<Application>();
    @ManyToMany(
            cascade = {
                    CascadeType.MERGE, CascadeType.PERSIST
            }
    )
    @JoinTable(name = "job_skill",
            joinColumns = {@JoinColumn(name = "job_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")}

    )
    private Set<Skill> skills = new HashSet<Skill>();


    public Job() {
        this.availble = true;
    }

    public Job(@NotBlank String title, @NotBlank String content, @NotBlank Long budget, @NotBlank int  delai, boolean availble, Client client) {
        this.title = title;
        this.content = content;
        this.budget = budget;
        this.delai = delai;
        this.availble = true;
        this.client = client;

    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
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

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public int  getDelai() {
        return delai;
    }

    public void setDelai(int  delai) {
        this.delai = delai;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Document> getFiles() {
        return files;
    }

    public void setFiles(Set<Document> files) {
        this.files = files;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }


    public void addSkill(Skill skill) {
        this.getSkills().add(skill);
        skill.getJobs().add(this);
    }

    public void removeSkill(Skill skill) {
        this.getSkills().remove(skill);
        skill.getJobs().remove(this);
    }

    public void addFile(Document document) {
        this.getFiles().add(document);
        document.setJob(this);
    }

    public void removeFile(Document document) {
        this.getFiles().remove(document);

    }

    public boolean isAvailble() {
        return availble;
    }

    public void setAvailble(boolean availble) {
        this.availble = availble;
    }
}
