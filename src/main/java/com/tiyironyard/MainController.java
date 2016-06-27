package com.tiyironyard;


import java.util.List;
import java.util.Set;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Taylor on 6/15/16.
 */
@Controller
public class MainController {



    @Autowired
    NoteRepository noteRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TagSearch tagSearch;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String root(HttpSession session, Model model){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser (HttpSession session, String userEmail, String password) throws Exception {
        //TODO: verify the user's existence by instantiating a new user by searching for their email in userRepository

        User user = userRepository.findByUserEmail(userEmail);
        if(user == null) {
            user = new User(userEmail, password);
            userRepository.save(user);
        }

        session.setAttribute("userEmail", userEmail);

        return "redirect:/home";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){

        session.removeAttribute("userEmail");
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String searchInput){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user == null){
            return "redirect:/login";
        }

        if(searchInput != null){
            List<Tag> tagResults = tagSearch.search(searchInput);


            model.addAttribute("tagResults", tagResults);
        }

        model.addAttribute("user", user);

        List<Note> userNotes = user.getNotes();
        Set<Tag> userTags = user.getTags();

        model.addAttribute("notes", userNotes);
        model.addAttribute("tags", userTags);

        return "home";
    }

    @RequestMapping(path = "/add-note", method = RequestMethod.POST)
    public String addNote(HttpSession session, String inputText, String tagInput) {
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user == null){
            return "redirect:/login";
        }
        //get current user's tags
        Set<Tag> userTagSet = user.getTags();
        //get current user's notes
        List<Note> userNoteList = user.getNotes();


        //instantiate a new note
        Note newNote = new Note();
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
            if (preExistingTag == null){

                //create a new tag and populate it
                Tag newTag = new Tag();
                newTag.setTagName(tagName);
                newTag.getUsers().add(user);

                //if current user can add the tag to their list (=true)
                //basically a final check that the tag doesn't already exist
                if (userTagSet.add(newTag)){
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

        //refresh the home page
        return "redirect:/home";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String list(Model model){

        return "list";
    }



    @RequestMapping(path = "/export", method = RequestMethod.GET)
    public String export(Model model){

        return "export";
    }



/*
    @RequestMapping(path = "/edit-tag", method = RequestMethod.POST)
    public String editTag(HttpSession session, String editTag){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user == null){
            return "redirect:/login";
        }

        //when the user clicks on the edit tag button
        //get the set of user's tags


        //find the tag with the same name
        //drop that tag from the user's list of tags
        //add the 'new' tag to user's tag set
        //add the 'new' tag to user's note's tag set.



        Set<Tag> userTagSet = user.getTags();


        return "redirect:/home";
    }*/
}
