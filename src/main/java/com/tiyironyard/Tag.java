package com.tiyironyard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
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
}
