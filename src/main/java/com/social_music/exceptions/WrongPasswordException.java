package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 15.11.2016.
 */
public class WrongPasswordException  extends LawAssistantException {
    public WrongPasswordException(){
        super("Wrong password");
    }

    public WrongPasswordException(String message){
        super(message);
    }

    @Override
    public int getCode() {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}
