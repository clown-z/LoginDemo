package com.clown.logindemo.service.impl;

import com.clown.logindemo.dao.UserDao;
import com.clown.logindemo.entity.User;
import com.clown.logindemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> queryAllUser() {
        return userDao.queryAllUser();
    }

    @Override
    public User queryUserById(Integer userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public User queryUserByName(String userName) {
        return userDao.queryUserByName(userName);
    }

    @Override
    public boolean addUser(User user) {
        boolean flag = false;
        int i = userDao.addUser(user);
        if(i > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyUser(User user) {
        boolean flag = false;
        int i = userDao.modifyUser(user);
        if(i > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean delUser(Integer userId) {
        boolean flag = false;
        int i = userDao.delUser(userId);
        if(i > 0) {
            flag = true;
        }
        return flag;
    }
}
