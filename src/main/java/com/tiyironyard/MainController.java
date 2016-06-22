package com.tiyironyard;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping(path="/", method = RequestMethod.GET)
    public String root(HttpSession session, Model model){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path="/login", method = RequestMethod.GET)
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
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(path="/home", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        User user = userRepository.findByUserEmail((String)session.getAttribute("userEmail"));

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        List<Note> userNotes = user.getNotes();
        Set<Tag> userTags = user.getTags();

        model.addAttribute("notes", userNotes);
        model.addAttribute("tags", userTags);

        return "home";
    }

    @RequestMapping(path="/add-note", method = RequestMethod.POST)
    public String addNote(HttpSession session, String inputText, String tagInput) {
        User user = userRepository.findByUserEmail((String) session.getAttribute("userEmail"));

        Set<Tag> userTagList = user.getTags();
        List<Note> userNoteList = user.getNotes();


        Note newNote = new Note();
        newNote.setNoteText(inputText);
        newNote.setUser(user);
        userNoteList.add(newNote);
        //user.setNotes(userNoteList);

        Set<Tag> noteTagList = newNote.getTags();

        //run through string and look for commas
        //if there are commas
        //split string on comma delimiter
        String[] splitTagString = tagInput.split(",");
        //trim starting and ending whitespace from split strings
        for (String string : splitTagString) {
            //trim string
            String tagName = string.trim();

            // getTagByName()
            Tag preExistingTag = tagRepository.findTagByTagName(tagName);
            // is this null?
            if(preExistingTag == null){
                //create a new tag and populate it
                Tag newTag = new Tag();
                newTag.setTagName(tagName);

                newTag.setUser(user);

                if(userTagList.add(newTag)){
                tagRepository.save(newTag);
                }
                noteTagList.add(newTag);
                newTag.getNotes().add(newNote);
            }else {
                noteTagList.add(preExistingTag);
                preExistingTag.getNotes().add(newNote);
            }
        }

        userRepository.save(user);
        noteRepository.save(newNote);

        return "redirect:/home";
    }

/*

            for(Tag tag : userTagList) {
                if (tag.equals(newTag)) {
                    System.out.println("This tag exists already");
                    tag.getNotes().add(newNote);
                    newNote.getTags().add(tag);
                    tagRepository.save(tag);

                    break;
                }
                else{
                    userTagList.add(newTag);
                    noteTagList.add(newTag);

                    tagRepository.save(newTag);

                    List<Note> tagNoteList = newTag.getNotes();
                    tagNoteList.add(newNote);
            }

        }*/





            //look through userTagList and pull out the tag names
            /*for (Tag tag : userTagList) {
                String existingTagName = tag.getTagName();
                //if the pulled name equals the input name
                if (existingTagName.equals(trimmedTag)) {
                    //scream and throw a tantrum
                    System.out.println("There is already a tag with this name!");
                    break;
                } else {*/







        //Tag newTag = new Tag();
        //newTag.setTagName(tagInput);
        //newTag.setUser(user);

        /*Note newNote = new Note();
        newNote.setNoteText(inputText);
        newNote.setUser(user);*/

        /*List<Note> userNoteList = user.getNotes();
        userNoteList.add(newNote);
        user.setNotes(userNoteList);*/

        //Set<Tag> userTagList = user.getTags();
        //userTagList.add(newTag);

        //List<Note> tagNoteList = newTag.getNotes();
        //tagNoteList.add(newNote);

        //Set<Tag> noteTagList = newNote.getTags();
        //noteTagList.add(newTag);






    @RequestMapping(path="/list", method = RequestMethod.GET)
    public String list(Model model){

        return "list";
    }

    @RequestMapping(path="/export", method = RequestMethod.GET)
    public String export(Model model){

        return "export";
    }
}
