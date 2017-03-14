package com.social_music.persistence.dao;

import com.social_music.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrii on 15.11.2016.
 */
@Transactional(propagation= Propagation.REQUIRED)
public interface UsersRepository extends JpaRepository<UserEntity, Integer> , JpaSpecificationExecutor {

    UserEntity findByEmail(String email);

    List<UserEntity> findAll();

    List<UserEntity> findAll(Sort sort);

    Page<UserEntity> findAll(Pageable pageable);
}

