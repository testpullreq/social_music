package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 23.12.2016.
 */
public class WrongRestrictionException extends LawAssistantException  {
    @Override
    public int getCode() {
        return HttpServletResponse.SC_CONFLICT;
    }

    @Override
    public String formMessage(){
        return "Wrong restriction";
    }
}
