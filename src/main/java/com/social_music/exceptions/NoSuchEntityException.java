package com.social_music.exceptions;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Andrii on 15.11.2016.
 */
public class NoSuchEntityException extends LawAssistantException {

    private static final String DEFAULT_MESSAGE = "No entity of type '%s' and params %s";
    private static final String NO_ENTITY_OF_TYPE = "No entity of type '%s'";

    public NoSuchEntityException(String className){
        super(String.format(NO_ENTITY_OF_TYPE , className));
    }

    public NoSuchEntityException(String className, String params){
        super(String.format(DEFAULT_MESSAGE, className, params));
    }

    public NoSuchEntityException(String className, int id) {
        super(String.format(DEFAULT_MESSAGE, className, "id: " + id));
    }

    public int getCode(){
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
