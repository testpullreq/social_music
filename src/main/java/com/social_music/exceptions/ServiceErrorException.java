package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 15.11.2016.
 */
public class ServiceErrorException extends LawAssistantException {

    @Override
    public int getCode(){
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    @Override
    public String formMessage() {
        return "Internal Server Error";
    }
}
