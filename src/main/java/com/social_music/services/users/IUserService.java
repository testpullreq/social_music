package com.social_music.services.users;

import com.social_music.exceptions.*;
import com.social_music.persistence.entities.UserEntity;
import com.social_music.views.UserView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrii on 15.11.2016.
 */
public interface IUserService {

    UserEntity getUserById(int userId) throws NoSuchEntityException;

    Map<String, Object> getUserByIdMap(int userId, Set<String> fields) throws NoSuchEntityException;

    List<UserEntity> getUsers(int offset, int limit) throws NoSuchEntityException, WrongRestrictionException;

    List<Map<String, Object>> getUsersMap(int offset, int limit, Set<String> fields) throws NoSuchEntityException, WrongRestrictionException;

    UserEntity getByEmail(String email) throws NoSuchEntityException;

    int create(UserView view) throws EmailExistsException, ServiceErrorException, ValidationException;

    UserEntity update(UserEntity user) throws NoSuchEntityException;
    boolean update(UserView view) throws NoSuchEntityException, EmailExistsException;
    boolean updatePassword(UserView view) throws NoSuchEntityException, WrongPasswordException;

    boolean signInUser(UserView view) throws NoSuchEntityException, WrongPasswordException;

    boolean logoutUser(HttpServletRequest request, HttpServletResponse response);

    long countUsers(String restrict) throws WrongRestrictionException;

    List<Map<String, Object>> getUsersMap(int offset, int limit, Set<String> fields, String locale, String restrict) throws NoSuchEntityException, WrongRestrictionException;
    List<UserEntity> getUsers(int offset, int limit, String restrict) throws WrongRestrictionException, NoSuchEntityException;

    Map<String, Object> getUserMap(Principal principal, Set<String> fields);
}
