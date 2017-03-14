package com.social_music.services.converters;

import com.social_music.persistence.entities.UserEntity;
import com.social_music.services.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.social_music.services.converters.Fields.User.*;

/**
 * Created by Andrii on 15.11.2016.
 */
@Component
public class UserConverter extends Converter<UserEntity> {

    @Override
    public Map<String, Object> convert(UserEntity object, Set<String> fields, String locale) {
        Map<String, Object> map = new HashMap<>();
        if(fields.contains(ID))
            map.put(ID, object.getId());
        if(fields.contains(EMAIL))
            map.put(EMAIL, object.getEmail());
        if(fields.contains(NICKNAME))
            map.put(NICKNAME, object.getNickname());
        if(fields.contains(FULL_NAME))
            map.put(NICKNAME, object.getFullName());
        if(fields.contains(ROLE))
            map.put(ROLE, object.getRole().getName());

        return map;
    }
}
