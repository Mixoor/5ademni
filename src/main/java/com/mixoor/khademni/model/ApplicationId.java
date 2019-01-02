package com.mixoor.khademni.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class ApplicationId implements Serializable {

    @Column(name = "freelancer_id")
    private Long freelancerId;

    @Column(name = "job_id")
    private Long jobId;

    public ApplicationId(Long freelancerId, Long jobId) {
        this.freelancerId = freelancerId;
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationId that = (ApplicationId) o;
        return Objects.equals(freelancerId, that.freelancerId) &&
                Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(freelancerId, jobId);
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
