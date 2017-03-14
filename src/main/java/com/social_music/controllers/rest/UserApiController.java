package com.social_music.controllers.rest;

import com.social_music.exceptions.LawAssistantException;
import com.social_music.pojo.other.Response;
import com.social_music.services.converters.Fields;
import com.social_music.services.users.IUserService;
import com.social_music.services.utils.ResponseFactory;
import com.social_music.services.utils.UserLocale;
import com.social_music.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrii on 15.11.2016.
 */
@Controller
@RequestMapping(value = "/api/users")
public class UserApiController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ResponseFactory responseFactory;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    public
    @ResponseBody
    Response<Map<String, Object>>
    getUser(
            @PathVariable("id") int userId,
            @RequestParam(value = "fields", required = false, defaultValue = Fields.User.DEFAULT) Set<String> fields
    ) throws LawAssistantException {
        return responseFactory.get(userService.getUserByIdMap(userId, fields));
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    public @ResponseBody Response<List<Map<String, Object>>>
    getUsers(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "fields", required = false, defaultValue = Fields.User.DEFAULT) Set<String> fields,
            @RequestParam(value = "restrict", required = false) String restrict,
            @RequestParam(value = LOCALE_NAME, defaultValue = UserLocale.UK) String locale
    ) throws LawAssistantException {
        return responseFactory.get(userService.getUsersMap(offset, limit, fields, locale, restrict));
    }

    @RequestMapping(
            value = "/count",
            method = RequestMethod.GET
    )
    public @ResponseBody Response<Long>
    countUsers(
            @RequestParam(value = "restrict", required = false) String restrict
    ) throws LawAssistantException {
        return responseFactory.get(userService.countUsers(restrict));
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.PUT
    )
    public
    @ResponseBody Response<Integer>
    createUser(
            @RequestBody UserView view
    ) throws LawAssistantException {
        return responseFactory.get(userService.create(view));
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.POST
    )
    public
    @ResponseBody Response<Boolean>
    updateUser(
            @RequestBody UserView view
    ) throws LawAssistantException {
        return responseFactory.get(userService.update(view));
    }

    @RequestMapping(
            value = "/password",
            method = RequestMethod.POST
    )
    public
    @ResponseBody Response<Boolean>
    updateUserPassword(
            @RequestBody UserView view
    ) throws LawAssistantException {
        return responseFactory.get(userService.updatePassword(view));
    }

    @RequestMapping(
            value = "/sign_in",
            method = RequestMethod.POST
    )
    public
    @ResponseBody Response<Boolean>
    signIn(
            @RequestBody UserView view
    ) throws LawAssistantException {
        return responseFactory.get(userService.signInUser(view));
    }

    @RequestMapping(
            value = "/logout",
            method = RequestMethod.POST
    )
    public
    @ResponseBody Response<Boolean>
    logout(HttpServletRequest request, HttpServletResponse response){
        return responseFactory.get(userService.logoutUser(request, response));
    }

    private static final String LOCALE_NAME = "locale";
}
