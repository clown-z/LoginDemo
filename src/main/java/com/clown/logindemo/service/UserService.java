package com.clown.logindemo.service;

import com.clown.logindemo.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> queryAllUser();

    /**
     * 通过ID查询用户信息
     * @param userId
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 通过用户名查询用户信息
     * @param userName
     * @return
     */
    User queryUserByName(String userName);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean modifyUser(User user);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    boolean delUser(Integer userId);
}
