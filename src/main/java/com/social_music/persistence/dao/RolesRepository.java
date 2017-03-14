package com.social_music.persistence.dao;

import com.social_music.pojo.enams.RolesEnum;
import com.social_music.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrii on 15.11.2016.
 */
@Transactional(propagation= Propagation.REQUIRED)
public interface RolesRepository extends JpaRepository<RoleEntity, Integer> , JpaSpecificationExecutor {
    RoleEntity findByName(RolesEnum name);
}
