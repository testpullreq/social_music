package com.social_music.services.roles;

import com.social_music.exceptions.*;
import com.social_music.persistence.dao.RolesRepository;
import com.social_music.persistence.entities.RoleEntity;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Brunets on 21.11.2016.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements IRoleService {

	 @Resource
	 private RolesRepository rolesRepository;
	
	@Override
	public RoleEntity changeRole(RoleEntity role) throws NoSuchEntityException {
		RoleEntity changedRole = rolesRepository.findOne(role.getId());
	    if (changedRole == null)
	    	throw new NoSuchEntityException(RoleEntity.class.getName(), "roleId "+role.getId());
	    changedRole.setName(role.getName());
	    return changedRole;
	}
	
}
