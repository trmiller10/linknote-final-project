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
    int id;

    String userEmail;
    String password;

    @OneToMany(mappedBy = "user")
    Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "user")
    List<Note> notes = new ArrayList<>();

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
}
