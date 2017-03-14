package com.social_music.persistence.restrictions;

import com.social_music.persistence.entities.RoleEntity_;
import com.social_music.persistence.entities.UserEntity;
import com.social_music.persistence.entities.UserEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii on 03.01.2017.
 */
public class UserRestriction {
    //user fields
    private Integer id;
    private String email;
    private String nickname;
    private Integer roleId;
    //restriction fields
    private List<Integer> ids;
    private String query;
    private List<Integer> roles;
    private Boolean active;

    public Specification<UserEntity> getSpecification(){
        return  new Specification<UserEntity>() {

            @Override
            public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (query!=null&&!"".equals(query)){
                    List<Predicate> orPredicates = new ArrayList<Predicate>();
                    orPredicates.add(cb.like(root.get(UserEntity_.email),"%"+query+"%"));
                    orPredicates.add(cb.like(root.get(UserEntity_.nickname),"%"+query+"%"));
                    predicates.add(cb.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
                }
                if (id!=null&&id>0)
                    predicates.add(cb.equal(root.get(UserEntity_.id),id));
                if (email!=null&&!"".equals(email))
                    predicates.add(cb.equal(root.get(UserEntity_.email),email));
                if (nickname!=null&&!"".equals(nickname))
                    predicates.add(cb.equal(root.get(UserEntity_.nickname),nickname));
                if (roleId!=null&&!"".equals(roleId))
                    predicates.add(cb.equal(root.get(UserEntity_.role).get(RoleEntity_.id),roleId));
                if (ids!=null&&ids.size()>0)
                    predicates.add(root.get(UserEntity_.id).in(ids));
                if (roles!=null&&roles.size()>0)
                    predicates.add(root.join(UserEntity_.role).get(RoleEntity_.id).in(roles));
                return cb.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }

    public UserRestriction(){}
    public UserRestriction(UserEntity user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.roleId = user.getRole().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
