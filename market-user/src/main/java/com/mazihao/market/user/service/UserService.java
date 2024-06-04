package com.mazihao.market.user.service;

import com.mazihao.market.common.exception.shengxianmarketException;
import com.mazihao.market.user.model.pojo.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    public void register(String username, String password) throws shengxianmarketException, NoSuchAlgorithmException;

    public User login(String username, String password) throws shengxianmarketException, NoSuchAlgorithmException;

    public void updateInformation(User user) throws shengxianmarketException;

    boolean checkAdminRole(User user);
}
