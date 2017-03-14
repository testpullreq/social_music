package com.social_music.services.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Converter<T> {

    public abstract Map<String, Object> convert(T object, Set<String> fields, String locale);

    public List<Map<String, Object>> convert(List<T> objects, Set<String> fields, String locale){
        List<Map<String, Object>> result = new ArrayList<>();
        for(T t : objects)
            result.add(convert(t, fields, locale));

        return result;
    }

}
