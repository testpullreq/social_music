package com.social_music.persistence.entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by Andrii on 03.01.2017.
 */
@StaticMetamodel( UserEntity.class )
public class UserEntity_ {
    public static volatile SingularAttribute<UserEntity, Integer> id;
    public static volatile SingularAttribute<UserEntity, String> email;
    public static volatile SingularAttribute<UserEntity, String> password;
    public static volatile SingularAttribute<UserEntity, String> nickname;
    public static volatile SingularAttribute<UserEntity, RoleEntity> role;
    public static volatile SingularAttribute<UserEntity, String> fullName;
}
