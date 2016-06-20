package com.tiyironyard;

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
    @GeneratedValue
    int id;

    String noteText;

    @ManyToMany
    @JoinTable(name = "note_tag",
            joinColumns = @JoinColumn(name = "notes_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "ID"))
    Set<Tag> tags = new HashSet<>();

    @ManyToOne
    User user;

    public Note(){}

    public Note(int id, String noteText) {
        this.id = id;
        this.noteText = noteText;
    }

    /*public Note(String noteText, Set<Tag> tags) {
        this.noteText = noteText;
        this.tags = tags;
    }
*/
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

    /*public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }*/
}
