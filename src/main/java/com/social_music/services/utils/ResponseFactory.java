package com.social_music.services.utils;

import com.social_music.pojo.other.Error;
import com.social_music.pojo.other.Response;
import org.springframework.stereotype.Component;

/**
 * Created by Andrii on 15.11.2016.
 */
@Component
public class ResponseFactory {

    public <T> Response<T> get(T t){
        Response<T> response = new Response<T>();
        response.setResult(t);
        return response;
    }

    public Response get(Error error) {
        Response response = new Response();
        response.setError(error);
        return response;
    }

}
