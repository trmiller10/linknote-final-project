package com.tiyironyard;

import javax.persistence.*;

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


    public Note(int id, String noteText) {
        this.id = id;
        this.noteText = noteText;
    }

    public Note(){}

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
}
