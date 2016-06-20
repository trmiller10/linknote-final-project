package com.tiyironyard;


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
    public String root(Model model){


        return "home";
    }

    @RequestMapping(path="/login", method = RequestMethod.GET)
    public String login(Model model){

        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String loginUser (HttpSession session, String userEmail, String password) throws Exception {
        //TODO: verify the user's existence by instantiating a new user by searching for their email in userRepository

        User user = userRepository.findFirstByUserEmail(userEmail);
        if(user == null) {
            user = new User(userEmail, password);
            userRepository.save(user);
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

        if(user==null){
            return "redirect:/login";
        } else if (user!=null){
            model.addAttribute("user", user);
        }

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
        tagRepository.save(newTag);

        Note newNote = new Note();
        newNote.setNoteText(inputText);
        newNote.setUser(user);
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
