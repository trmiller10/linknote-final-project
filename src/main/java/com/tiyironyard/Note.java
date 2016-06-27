package com.tiyironyard;

import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Taylor on 6/16/16.
 */
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = “userIdSequence")
    private int id;

    @Lob
    @Type(type = "text")
    private String noteText;

    @ManyToMany/*(cascade=CascadeType.ALL)*/
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "notes_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "ID"))
    private Set<Tag> tags = new HashSet<>();


    @ManyToOne
    private User user;

    //boolean checked = false;

    public Note(){}

    public Note(int id, String noteText) {
        this.id = id;
        this.noteText = noteText;
    }
    public Note(String noteText, User user) {
        this.noteText = noteText;
        this.user = user;
    }

    public Note(String noteText, Set<Tag> tags) {
        this.noteText = noteText;
        this.tags = tags;
    }

    public Note(String noteText, Set<Tag> tags, User user) {
        this.noteText = noteText;
        this.tags = tags;
        this.user = user;
    }

    public Note(String noteText){
        this.noteText = noteText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
