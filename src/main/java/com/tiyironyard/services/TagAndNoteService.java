package com.tiyironyard.services;

import com.tiyironyard.entities.Note;
import com.tiyironyard.entities.Tag;
import com.tiyironyard.entities.User;
import com.tiyironyard.repositories.NoteRepository;
import com.tiyironyard.repositories.TagRepository;
import com.tiyironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by Taylor on 6/28/16.
 */
@Service
public class TagAndNoteService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;


    public void saveNoteAndTags(User user, String inputText, String tagInput, Integer id){

        //pull in the user found in the route

        //get current user's tags
        Set<Tag> userTagSet = user.getTags();
        //get current user's notes
        List<Note> userNoteList = user.getNotes();

        //instantiate a new note
        Note newNote = new Note();


        if (id != null && id != 0 ) {
            newNote = noteRepository.findByIdAndUserId(id, user.getId());
            if(!newNote.getNoteText().equals(inputText)/* && !tagInput.equals()*/) {
                newNote.getTags().clear();
            }
        }

        //set newNote's text property to the input text
        newNote.setNoteText(inputText);
        //set newNote's user property to the current user
        newNote.setUser(user);
        //add the newNote to current user's note list
        userNoteList.add(newNote);

        //get the newNote's set of tags (should be zero)
        Set<Tag> noteTagSet = newNote.getTags();

        //split the input tag string into a string array based on commas
        String[] splitTagString = tagInput.split(",");

        //for each string in the string array
        for (String string : splitTagString) {
            //trim starting and ending whitespace from split strings
            String tagName = string.trim();

            // instantiate a new Tag object and look through tagRepository if new tag's name already exists
            Tag preExistingTag = tagRepository.findTagByTagName(tagName);


            // is this null?
            if (preExistingTag == null) {

                //create a new tag and populate it
                Tag newTag = new Tag();
                newTag.setTagName(tagName);
                newTag.getUsers().add(user);

                //if current user can add the tag to their list (=true)
                //basically a final check that the tag doesn't already exist
                if (userTagSet.add(newTag)) {
                    //save the new tag in the tagRepository
                    tagRepository.save(newTag);
                }
                //otherwise add the new tag to the new note's tag list
                noteTagSet.add(newTag);
                //add the new note to the new tag's list of notes
                newTag.getNotes().add(newNote);


            } else { //if there already is a tag with the same name
                //add the old tag to the new note's tag set
                noteTagSet.add(preExistingTag);
                //add the new note to the old tag's list of notes
                preExistingTag.getNotes().add(newNote);
                //add the old tag to the current user's tag set
                //if it already exists, it shouldn't be duplicated
                userTagSet.add(preExistingTag);
                //add the current user to the old tag's list of users
                preExistingTag.getUsers().add(user);
                //save the changes made to the old tag into the tag repository
                tagRepository.save(preExistingTag);
            }
        }
        //save the changes made to the current user and the new note into their respective repositories
        userRepository.save(user);
        noteRepository.save(newNote);

    }
}
