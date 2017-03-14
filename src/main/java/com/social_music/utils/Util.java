package com.social_music.utils;

import org.springframework.stereotype.Service;

/**
 * Created by Oleh on 13.03.2017.
 */
@Service
public class Util {

    public long countPages(long numberOFEntities, int perPage) {
        long result = numberOFEntities / perPage;
        if (numberOFEntities % perPage != 0)
            return result + 1;
        return result;
    }

}
