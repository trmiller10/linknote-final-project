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

    @RequestMapping(path="/home", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){

        Iterable<Note> notes = noteRepository.findAll();

        Iterable<Tag> tags = tagRepository.findAll();

        model.addAttribute("notes", notes);
        return "home";
    }

    @RequestMapping(path="/add-note", method = RequestMethod.POST)
    public String addNote(String inputText, String tagName){

        Tag newTag = new Tag(tagName);
        tagRepository.save(newTag);

        Note newNote = new Note(inputText);
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
