package com.social_music.services.utils;

import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Oleh on 04.02.2017.
 */
@Service
public class ValidationUtil {

    public boolean valid(String s) {
        return s != null;
    }

    public boolean valid(Integer i) {
        return i != null && i > 0;
    }

    public boolean valid(Collection collection) {
        return collection != null && !collection.isEmpty();
    }
}
