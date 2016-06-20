package com.tiyironyard;





import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taylor on 6/16/16.
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    int id;
    String tagName;

    @ManyToMany(mappedBy = "tags")
    List<Note> notes = new ArrayList<>();

    @ManyToOne
    User user;


    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(String tagName, User user) {
        this.tagName = tagName;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
