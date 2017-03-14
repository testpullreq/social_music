package com.social_music.services.roles;

import com.social_music.exceptions.*;
import com.social_music.persistence.entities.RoleEntity;


/**
 * Created by Brunets on 15.11.2016.
 */
public interface IRoleService {
    
    RoleEntity changeRole(RoleEntity role) throws NoSuchEntityException;
}
