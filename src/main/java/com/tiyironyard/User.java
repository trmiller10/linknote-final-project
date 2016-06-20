package com.tiyironyard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Taylor on 6/17/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String userEmail;
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Note> notes = new ArrayList<>();

    public User(){}

    public User(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
