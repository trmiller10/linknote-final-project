package com.tiyironyard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Taylor on 6/15/16.
 */
@Controller
public class MainController {

    @RequestMapping(path="/", method = RequestMethod.GET)
    public String root(Model model){


        return "login";
    }

    @RequestMapping(path="/login", method = RequestMethod.GET)
    public String login(Model model){

        return "login";
    }

    @RequestMapping(path="/home", method = RequestMethod.GET)
    public String home(Model model){

        return "home";
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
