package com.social_music.persistence.entities;

import com.social_music.pojo.enams.RolesEnum;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by Andrii on 03.01.2017.
 */
@StaticMetamodel( RoleEntity.class )
public class RoleEntity_ {
    public static volatile SingularAttribute<RoleEntity, Integer> id;
    public static volatile SingularAttribute<RoleEntity, RolesEnum> name;
    public static volatile SetAttribute<RoleEntity, PermissionEntity> permissions;

}
