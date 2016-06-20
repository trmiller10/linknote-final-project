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
        User user = userRepository.findFirstByUserEmail((String)session.getAttribute("userEmail"));

        if(user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path="/login", method = RequestMethod.GET)
    public String login(Model model, HttpSession session){
        User user = userRepository.findFirstByUserEmail((String)session.getAttribute("userEmail"));

        if(user != null){
            return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser (HttpSession session, String userEmail, String password) throws Exception {
        //TODO: verify the user's existence by instantiating a new user by searching for their email in userRepository

        User user = userRepository.findFirstByUserEmail(userEmail);
        if(user == null) {
            user = new User(userEmail, password);
            userRepository.save(user);
        } else if(user != null){

        }
        session.setAttribute("userEmail", userEmail);

        return "redirect:/home";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(path="/home", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        User user = userRepository.findFirstByUserEmail((String)session.getAttribute("userEmail"));

        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        Iterable<Note> notes = noteRepository.findAll();

        Iterable<Tag> tags = tagRepository.findAll();

        model.addAttribute("notes", notes);
        model.addAttribute("tags", tags);

        return "home";
    }

    @RequestMapping(path="/add-note", method = RequestMethod.POST)
    public String addNote(HttpSession session, String inputText, String tagName){
        User user = userRepository.findFirstByUserEmail((String)session.getAttribute("userEmail"));


        Tag newTag = new Tag();
        newTag.setTagName(tagName);
        newTag.setUser(user);

        Note newNote = new Note();
        newNote.setNoteText(inputText);
        newNote.setUser(user);

        List<Note> userNoteList = user.getNotes();
        userNoteList.add(newNote);
        user.setNotes(userNoteList);

        Set<Tag> userTagList = user.getTags();
        userTagList.add(newTag);
        //user.setTags(userTagList);

        List<Note> tagNoteList = newTag.getNotes();
        tagNoteList.add(newNote);
        //newTag.setNotes(tagNoteList);

        Set<Tag> noteTagList = newNote.getTags();
        noteTagList.add(newTag);
        //newNote.setTags(noteTagList);

        userRepository.save(user);
        tagRepository.save(newTag);
        noteRepository.save(newNote);


        return "redirect:/home";
    }

    @RequestMapping(path="/list", method = RequestMethod.GET)
    public String list(Model model){

        return "list";
    }

    @RequestMapping(path="/export", method = RequestMethod.GET)
    public String export(Model model){

        return "export";
    }
}
