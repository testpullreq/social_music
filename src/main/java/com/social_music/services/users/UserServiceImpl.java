package com.social_music.services.users;

import com.social_music.persistence.entities.*;
import com.social_music.persistence.restrictions.RestrictionsFields;
import com.social_music.persistence.restrictions.UserRestriction;
import com.social_music.pojo.enams.RolesEnum;
import com.social_music.exceptions.*;
import com.social_music.persistence.dao.RolesRepository;
import com.social_music.persistence.dao.UsersRepository;
import com.social_music.services.converters.Converter;
import com.social_music.services.utils.SessionUtils;
import com.social_music.services.utils.ValidationUtil;
import com.social_music.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrii on 15.11.2016.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements IUserService {

    @Resource
    private UsersRepository usersRepository;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private IUserValidateService userValidateService;

    @Resource
    private RolesRepository rolesRepository;

    @Autowired
    private Converter<UserEntity> userConverter;

    @Autowired
    private ValidationUtil validationUtil;

    @Override
    @Transactional
    public UserEntity getUserById(int userId) throws NoSuchEntityException {
        UserEntity user = usersRepository.findOne(userId);
        if (user == null)
            throw new NoSuchEntityException("user", userId);
        return user;
    }

    @Override
    @Transactional
    public Map<String, Object> getUserByIdMap(int userId, Set<String> fields) throws NoSuchEntityException {
        return userConverter.convert(getUserById(userId), fields,LocaleContextHolder.getLocale().getLanguage());
    }

    @Override
    @Transactional
    /**
     * offset number of rows to skip
     * limit max on request
     */
    public List<UserEntity> getUsers(int offset, int limit) throws NoSuchEntityException, WrongRestrictionException {
        return getUsers(offset,limit,null);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getUsersMap(int offset, int limit, Set<String> fields) throws NoSuchEntityException, WrongRestrictionException {
        return userConverter.convert(getUsers(offset, limit), fields, LocaleContextHolder.getLocale().getLanguage());
    }

    @Override
    @Transactional
    public UserEntity getByEmail(String email) throws NoSuchEntityException {
        UserEntity find = usersRepository.findByEmail(email);
        if (find == null)
            throw new NoSuchEntityException(UserEntity.class.getName(),"user email "+email);
        return find;
    }

    @Override
    @Transactional
    public int create(UserView view) throws EmailExistsException, ServiceErrorException, ValidationException {
        try {
            getByEmail(view.getEmail());
            // should be exception
            // otherwise user exists and exception should be thrown
            throw new EmailExistsException();
        } catch (NoSuchEntityException e) {
            UserEntity entity = new UserEntity();
            if (view.getRole() == null)
                view.setRole(RolesEnum.user);
            merge(entity, view);
            if (sessionUtils.isUserWithRole(RolesEnum.admin, RolesEnum.moderator)) {
                view.setPassword("default_password");
                entity.setPassword("default_password");
            }
            userValidateService.validForCreate(view);
            entity = usersRepository.saveAndFlush(entity);
            if(entity == null){
                throw new ServiceErrorException();
            }
            if (!sessionUtils.isAuthorized()){
                sessionUtils.logeInUser(entity);
            }
            return entity.getId();
        }
    }

    @Override
    @Transactional(rollbackFor=NoSuchEntityException.class)
    public UserEntity update(UserEntity user) throws NoSuchEntityException {
        UserEntity updatedUser = usersRepository.findOne(user.getId());
        if (updatedUser == null)
            throw new NoSuchEntityException("user", user.getId());
        updatedUser.setNickname(user.getNickname());
        updatedUser.setEmail(user.getEmail());
        //TODO add oll other
        return updatedUser;
    }

    @Override
    @Transactional
    public boolean update(UserView view) throws NoSuchEntityException, EmailExistsException {
        UserEntity updatedUser = usersRepository.findOne(view.getId());
        if (updatedUser == null)
            throw new NoSuchEntityException("user", view.getId());

        if (view.getEmail() != null) {
            UserEntity byEmail = usersRepository.findByEmail(view.getEmail());
            if (byEmail != null && byEmail.getId() != updatedUser.getId())
                throw new EmailExistsException();
        }

        merge(updatedUser, view);
        updatedUser = usersRepository.saveAndFlush(updatedUser);

        return updatedUser != null;
    }

    @Override
    public boolean updatePassword(UserView view) throws NoSuchEntityException, WrongPasswordException {
        UserEntity entity = getUserById(view.getId());
        if (!entity.getPassword().equals(view.getOld_password()))
            throw new WrongPasswordException("old password is incorrect");

        entity.setPassword(view.getNew_password());
        entity = update(entity);

        return entity != null;
    }

    @Override
    @Transactional
    public boolean signInUser(UserView view) throws NoSuchEntityException, WrongPasswordException {
        UserEntity entity = getByEmail(view.getEmail());
        if(!entity.getPassword().equals(view.getPassword()))
            throw new WrongPasswordException();

        Authentication authentication = new UsernamePasswordAuthenticationToken(entity, entity.getPassword(), getGrantedAuthorities(entity));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        return true;
    }

    @Override
    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return true;
    }

    @Override
    @Transactional
    public long countUsers(String restrict) throws WrongRestrictionException {
        Long count;
        if (restrict == null||"".equals(restrict)){
            count = usersRepository.count();
        }else{
            try {
                UserRestriction restriction = RestrictionsFields.User.parseString(restrict);
                count = usersRepository.count(restriction.getSpecification());
            } catch (IOException e) {
                throw new WrongRestrictionException();
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getUsersMap(int offset, int limit, Set<String> fields, String locale, String restrict) throws NoSuchEntityException, WrongRestrictionException {
        return userConverter.convert(getUsers(offset,limit,restrict),fields,locale);
    }

    @Override
    public List<UserEntity> getUsers(int offset, int limit, String restrict) throws WrongRestrictionException, NoSuchEntityException {
        Page<UserEntity> users = null;
        if (restrict == null||"".equals(restrict)){
            users = usersRepository.findAll(new PageRequest(offset/limit,limit));
        }else{
            try {
                UserRestriction restriction = RestrictionsFields.User.parseString(restrict);
                users = usersRepository.findAll(restriction.getSpecification(),new PageRequest(offset/limit,limit));
            } catch (IOException e) {
                throw new WrongRestrictionException();
            }
        }
        if(users == null || users.getContent().isEmpty())
            throw new NoSuchEntityException("users", String.format("[offset: %d, limit: %d]", offset, limit));
        return users.getContent();
    }

    @Override
    public Map<String, Object> getUserMap(Principal principal, Set<String> fields) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)principal;
        UserEntity user = (UserEntity)authentication.getPrincipal();

        try {
            user = getUserById(user.getId());
        } catch (NoSuchEntityException e) {

        }

        return userConverter.convert(user, fields, LocaleContextHolder.getLocale().getLanguage());
    }

    private List<GrantedAuthority> getGrantedAuthorities(UserEntity user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (PermissionEntity perm : user.getRole().getPermissions()){
            authorities.add(new SimpleGrantedAuthority(perm.getName()));
        }
        return authorities;
    }

    public void merge(UserEntity entity, UserView view){
        if(validationUtil.valid(view.getNickname()))
            entity.setNickname(view.getNickname());
        else view.setNickname(entity.getNickname());

        if(validationUtil.valid(view.getFull_name()))
            entity.setFullName(view.getFull_name());
        else view.setFull_name(entity.getFullName());

        if(validationUtil.valid(view.getEmail()))
            entity.setEmail(view.getEmail());
        else view.setEmail(entity.getEmail());


        if(validationUtil.valid(view.getPassword()))
            entity.setPassword(view.getPassword());
        else view.setPassword(entity.getPassword());

        if (view.getRole() != null){
            RoleEntity role = rolesRepository.findByName(view.getRole());
            entity.setRole(role);
        }else if (entity.getRole()!=null){
            view.setRole(entity.getRole().getName());
        }

    }
}
