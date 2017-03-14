package com.social_music.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Oleh on 11.03.2017.
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController {

    @RequestMapping(value = "/messages/{name}.js", method = RequestMethod.GET)
    public String getMessages(@PathVariable("name") String file,
                              Locale locale,
                              Model model){
        ResourceBundle bundle = ResourceBundle.getBundle(file, locale);
        model.addAttribute("keys", bundle.getKeys());
        model.addAttribute("var", file);
        return "resources/messages";
    }

}
