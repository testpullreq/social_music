package com.social_music.persistence.restrictions;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Andrii on 22.12.2016.
 */
public class RestrictionsFields {

    public static final class User{
        public static UserRestriction parseString(String json) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            UserRestriction restriction = mapper.readValue(json,UserRestriction.class);
            return restriction;
        }
    }
}
