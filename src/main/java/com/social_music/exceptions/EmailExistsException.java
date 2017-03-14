package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 15.11.2016.
 */
public class EmailExistsException extends LawAssistantException {

    @Override
    public int getCode() {
        return HttpServletResponse.SC_CONFLICT;
    }

    @Override
    public String formMessage(){
        return "User with this email already exists";
    }
}
