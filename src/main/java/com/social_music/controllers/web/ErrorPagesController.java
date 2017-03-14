package com.social_music.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Oleh on 24.02.2017.
 */
@Controller
@RequestMapping("/error")
public class ErrorPagesController {

    @RequestMapping(value = "not_found", method = RequestMethod.GET)
    public String notFound(){
        return "errors/404";
    }

    @RequestMapping(value = "access_denied", method = RequestMethod.GET)
    public String accessDenied(){
        return "errors/403";
    }
}
