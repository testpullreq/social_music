package com.social_music.services.users;

import com.social_music.exceptions.ServiceErrorException;
import com.social_music.exceptions.ValidationException;
import com.social_music.views.UserView;

/**
 * Created by Andrii on 15.11.2016.
 */
public interface IUserValidateService {
    void validForCreate(UserView user) throws ServiceErrorException, ValidationException;
}
