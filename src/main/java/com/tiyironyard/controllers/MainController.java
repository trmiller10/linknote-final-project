package com.tiyironyard.controllers;


import java.util.*;
import java.util.stream.Collectors;

import com.tiyironyard.entities.Note;
import com.tiyironyard.entities.Tag;
import com.tiyironyard.entities.User;
import com.tiyironyard.repositories.NoteRepository;
import com.tiyironyard.repositories.TagRepository;
import com.tiyironyard.repositories.UserRepository;
import com.tiyironyard.security.PasswordStorage;
import com.tiyironyard.services.TagAndNoteService;
import com.tiyironyard.services.TagSearch;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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


    @Autowired
    private TagAndNoteService tagAndNoteService;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String root(HttpSession session, Model model) {
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        if (user != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session) {
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        if (user != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser(HttpSession session, Model model, String userEmail, String password) throws Exception {
        User user = userRepository.findByUserEmail(userEmail);
        if (user == null) {
            throw new Exception("Incorrect email and/or password");
        } else if (PasswordStorage.verifyPassword(password, user.getPassword())){
            session.setAttribute("userEmail", userEmail);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())){
        session.setAttribute("userEmail", userEmail);
            throw new Exception("Incorrect email and/or password");
    }
        return "redirect:/";
    }

    @RequestMapping(path = "/create-user", method = RequestMethod.POST)
    public String createUser(HttpSession session, Model model, String userEmail, String newEmail, String newPassword) throws Exception {
        User user = userRepository.findByUserEmail(newEmail);
        if (user != null) {
            throw new Exception("Entered email is already in use.");
        }
            user = new User(newEmail, PasswordStorage.createHash(newPassword));
            userRepository.save(user);
            session.setAttribute("userEmail", newEmail);

        return "redirect:/home";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.removeAttribute("userEmail");
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public String home(Model model,
                       HttpSession session,
                       String searchInput,
                       Integer id,
                       String resetSearch,
                       String[] noteId) {
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        if (user == null) {
            return "redirect:/login";
        }

        if (id != null && id != 0 && noteId == null) {
            model.addAttribute("note", noteRepository.findByIdAndUserId(id, user.getId()));
        } else {
            model.addAttribute("note", new Note());
        }

        List<Note> userNotes = new ArrayList<>();

        if (resetSearch != null) {
            searchInput = null;
            userNotes = user.getNotes();
        }

        if (searchInput != null) {
            //try {
            List<Tag> tagResults = tagSearch.search(searchInput);
            //} catch (EmptyQueryException e) {
            //    List<Tag> tagResults = stopWordSearch.search(searchInput);
            //}
            List<Note> noteSet = new ArrayList<>();
            Set<Note> filterNotes = new HashSet<>();
            List<Note> filteredList = new ArrayList<>();
            for(Tag tag : tagResults){
                noteSet = tag.getNotes();
            }
            filterNotes.addAll(noteSet);
            for(Note note : filterNotes){
                if(note.getTags().containsAll(tagResults)){
                    filteredList.add(note);
                }
            }

            userNotes = filteredList;

        } else {
            userNotes = user.getNotes();
        }
       /* try{
        if (noteId != null && searchInput == null && id == null) {
            List<String> joinNoteText = new ArrayList<>();
            for (String foundId : noteId) {
                //get the note associated with the current id and user
                Note checkedNote = noteRepository.findByIdAndUserId(Integer.valueOf(foundId), user.getId());
                //pull out that note's text
                String text = checkedNote.getNoteText();

                joinNoteText.add(text);
            }


            String superNoteText = joinNoteText.stream().collect(Collectors.joining());
            model.addAttribute("note", superNoteText);
        }
        }catch (NullPointerException e){
            e.printStackTrace();
        }*/

        model.addAttribute("user", user);

        Set<Tag> userTags = user.getTags();

        model.addAttribute("notes", userNotes);
        model.addAttribute("tags", userTags);

        return "home";
    }

    @RequestMapping(path = "/add-note", method = RequestMethod.POST)
    public String addNote(HttpSession session, String inputText, String tagInput, Integer id) {
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        if (user == null) {
            return "redirect:/login";
        }

        tagAndNoteService.saveNoteAndTags(user, inputText, tagInput, id);


        /*//get current user's tags
        Set<Tag> userTagSet = user.getTags();
        //get current user's notes
        List<Note> userNoteList = user.getNotes();

        //instantiate a new note
        Note newNote = new Note();


        if (id != null && id != 0) {
            newNote = noteRepository.findByIdAndUserId(id, user.getId());
            newNote.getTags().clear();
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
        noteRepository.save(newNote);*/

        //refresh the home page
        return "redirect:/home";
    }

    @RequestMapping(path = "/bulk-tag", method = RequestMethod.POST)
    public String bulkTag(HttpSession session,
                          @RequestParam List<Integer> noteId,
                          @RequestParam List<String> selectTag){

        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        if (user == null) {
            return "redirect:/login";
        }
        //for each id in the noteId list
        for (Integer id : noteId) {
            //get the note associated with the current id and user
            Note checkedNote = noteRepository.findByIdAndUserId(id, user.getId());
            //pull out that note's text
            String text = checkedNote.getNoteText();
            //pull out that note's tag set
            Set<Tag> noteTags = checkedNote.getTags();

            //for each tagName in that note's tag set
            for (String tagName : selectTag) {

                tagAndNoteService.saveNoteAndTags(user, text, tagName, id);
            }
        }

        return "redirect:/home";
    }
//TODO: catch errors when user has nothing selected
    @RequestMapping(path = "/merge-notes", method = RequestMethod.POST)
    public String mergeNotes(HttpSession session,
                             Integer id,
                             @RequestParam List<Integer> noteId,
                             @RequestParam List<String> selectTag){
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));
        if (user == null) {
            return "redirect:/login";
        }
        if (noteId == null){
            return "redirect:/home";
        }
        List<String> joinNoteText = new ArrayList<>();
        Set<Tag> noteTags = new HashSet<>();
        List<String> joinTag = new ArrayList<>();
        for (Integer foundId : noteId) {
            //get the note associated with the current id and user
            Note checkedNote = noteRepository.findByIdAndUserId(foundId, user.getId());
            //pull out that note's text
            String text = checkedNote.getNoteText();

            noteTags = checkedNote.getTags();
            for (Tag tag : noteTags){
                String tagName = tag.getTagName();
                joinTag.add(tagName);
            }
            joinNoteText.add(text);
        }

        String superNoteText = joinNoteText.stream().collect(Collectors.joining("\n"));

        String superTagSelect = joinTag.stream().collect(Collectors.joining(", "));
        //for each tagName in that note's tag set

        tagAndNoteService.saveNoteAndTags(user, superNoteText, superTagSelect, id);


        return "redirect:/home";
    }

    @RequestMapping(path = "/delete-notes", method = RequestMethod.POST)
    public String deleteNotes(HttpSession session, @RequestParam List<Integer> noteId){
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));
        if (user == null) {
            return "redirect:/login";
        }
        if (noteId == null){
            return "redirect:/home";
        }
        for(Integer id : noteId){
            noteRepository.delete(noteRepository.findByIdAndUserId(id, user.getId()));
        }

        return "redirect:/home";
    }
}
