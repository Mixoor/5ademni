package com.mixoor.khademni.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Conversation extends DateAudit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user")
    @NotBlank
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seconde_user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotBlank
    User user2;
    @Temporal(TemporalType.TIMESTAMP)
    Date time;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private int status = 0;

    public Conversation(@NotBlank User user1, @NotBlank User user2, Date time, int status) {
        this.user1 = user1;
        this.user2 = user2;
        this.time = time;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
