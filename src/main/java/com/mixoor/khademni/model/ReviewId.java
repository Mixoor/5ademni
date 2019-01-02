package com.mixoor.khademni.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewId implements Serializable {


    @Column(name = "freelancer_id", nullable = false)
    private Long freelancerId;


    @Column(name = "client_id", nullable = false)
    private Long clientId;


    public ReviewId(Long freelancerId, Long clientId) {
        this.freelancerId = freelancerId;
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(freelancerId, reviewId.freelancerId) &&
                Objects.equals(clientId, reviewId.clientId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(freelancerId, clientId);
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
