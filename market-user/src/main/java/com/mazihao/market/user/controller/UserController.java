package com.mazihao.market.user.controller;

import com.mazihao.market.common.common.ApiRestResponse;
import com.mazihao.market.common.common.Constant;
import com.mazihao.market.common.exception.shengxianmarketException;
import com.mazihao.market.common.exception.shengxianmarketExceptionEnum;
import com.mazihao.market.user.model.pojo.User;
import com.mazihao.market.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws shengxianmarketException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_PASSWORD);
        }
        // 密码长度不能小于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(username, password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws NoSuchAlgorithmException, shengxianmarketException {
        if(StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        user.setPassword(null);
        session.setAttribute(Constant.USER, user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam("signature") String signature) throws shengxianmarketException {
        User currentUser = (User)session.getAttribute(Constant.USER);
        if (currentUser == null) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.USER);
        return ApiRestResponse.success();
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws NoSuchAlgorithmException, shengxianmarketException {
        if(StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_USER_NAME);
        }
        if(StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        // 校验是不是管理员
        if (userService.checkAdminRole(user)) {
            user.setPassword(null);
            session.setAttribute(Constant.USER, user);
        } else {
            return ApiRestResponse.error(shengxianmarketExceptionEnum.NEED_ADMIN);
        }
        return ApiRestResponse.success(user);
    }

    @PostMapping("/checkAdminRole")
    @ResponseBody
    public Boolean checkAdminRole(@RequestBody User user) {
        return userService.checkAdminRole(user);
    }

    @GetMapping("/getuser")
    @ResponseBody
    public User getuser(HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.USER);
        return currentUser;
    }
}
