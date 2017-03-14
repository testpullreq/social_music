package com.social_music.services.users;

import com.social_music.pojo.enams.RolesEnum;
import com.social_music.exceptions.ServiceErrorException;
import com.social_music.exceptions.ValidationException;
import com.social_music.persistence.entities.UserEntity;
import com.social_music.services.utils.SessionUtils;
import com.social_music.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserValidateServiceImpl implements IUserValidateService {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private Validator validator;

    @Override
    public void validForCreate(UserView user) throws ServiceErrorException, ValidationException {
        Set<ConstraintViolation<UserView>> violations = validator.validate(user);
        if(violations != null && !violations.isEmpty()) {
            throw new ValidationException(UserEntity.class.getName(), violations);
        }
        if (!sessionUtils.isAuthorized()){
            if (user.getRole()==null||!user.getRole().equals(RolesEnum.user)){
                throw new ServiceErrorException();
            }
        }else if (!sessionUtils.isUserWithRole(RolesEnum.admin)){
            throw new ServiceErrorException();
        }
    }
}
