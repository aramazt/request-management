package com.prime.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prime.task.utils.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String fullname;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "responsible",
            cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Request> requests;

    private User() {
        this.requests = new ArrayList<>();
    }

    public User(String login, String fullname, UserStatus status) {
        this();
        this.login = login;
        this.fullname = fullname;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
