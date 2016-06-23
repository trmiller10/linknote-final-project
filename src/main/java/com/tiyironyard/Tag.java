package com.tiyironyard;





import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Taylor on 6/16/16.
 */
@Entity
@Table(name = "tags")
@Indexed
public class Tag {

    @Id
    @GeneratedValue
    private int id;

    @Field
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    @IndexedEmbedded
    private List<Note> notes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tag_user",
            joinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"))
    @IndexedEmbedded
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return tagName.equals(tag.tagName);

    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(String tagName, List<Note> notes, Set<User> users) {
        this.tagName = tagName;
        this.notes = notes;
        this.users = users;
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


    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
