package com.prime.task.model;

import com.prime.task.utils.RequestStatus;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User responsible;

    private String prevResponsible;

    public Request() {
    }

    public Request(String name, String description, User responsible) {
        this.name = name;
        this.description = description;
        this.responsible = responsible;
        this.status = RequestStatus.OPEN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setPrevResponsible(String prevResponsible) {
        this.prevResponsible = prevResponsible;
    }

    public String getPrevResponsible() {
        return prevResponsible;
    }
}
