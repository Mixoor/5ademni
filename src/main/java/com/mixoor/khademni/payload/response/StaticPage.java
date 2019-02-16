package com.mixoor.khademni.payload.response;

public class StaticPage {
    Long users,clients,freelancers,jobsFinished,jobsUnfinished,applications,post,comments,projects,votes,tickets;

    public StaticPage(Long users, Long clients, Long freelancers, Long jobsFinished, Long jobsUnfinished, Long applications, Long post, Long comments, Long projects, Long votes, Long tickets) {
        this.users = users;
        this.clients = clients;
        this.freelancers = freelancers;
        this.jobsFinished = jobsFinished;
        this.jobsUnfinished = jobsUnfinished;
        this.applications = applications;
        this.post = post;
        this.comments = comments;
        this.projects = projects;
        this.votes = votes;
        this.tickets = tickets;
    }

    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }

    public Long getClients() {
        return clients;
    }

    public void setClients(Long clients) {
        this.clients = clients;
    }

    public Long getFreelancers() {
        return freelancers;
    }

    public void setFreelancers(Long freelancers) {
        this.freelancers = freelancers;
    }

    public Long getJobsFinished() {
        return jobsFinished;
    }

    public void setJobsFinished(Long jobsFinished) {
        this.jobsFinished = jobsFinished;
    }

    public Long getJobsUnfinished() {
        return jobsUnfinished;
    }

    public void setJobsUnfinished(Long jobsUnfinished) {
        this.jobsUnfinished = jobsUnfinished;
    }

    public Long getApplications() {
        return applications;
    }

    public void setApplications(Long applications) {
        this.applications = applications;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getProjects() {
        return projects;
    }

    public void setProjects(Long projects) {
        this.projects = projects;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Long getTickets() {
        return tickets;
    }

    public void setTickets(Long tickets) {
        this.tickets = tickets;
    }
}
