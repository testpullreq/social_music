package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 15.11.2016.
 */
public class AuthRequiredException extends LawAssistantException {

    @Override
    public int getCode(){
        return HttpServletResponse.SC_UNAUTHORIZED;
    }

    @Override
    public String formMessage() {
        return "You must be authorized!";
    }

}
