package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 22.12.2016.
 */
public class CountryCodeExistException extends LawAssistantException  {
    @Override
    public int getCode() {
        return HttpServletResponse.SC_CONFLICT;
    }

    @Override
    public String formMessage(){
        return "Country with this code already exists";
    }
}
