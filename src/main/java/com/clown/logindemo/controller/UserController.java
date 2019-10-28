package com.clown.logindemo.controller;

import com.clown.logindemo.entity.User;
import com.clown.logindemo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/all")
    public Map<String, Object> queryAllUser() {
        Map<String, Object> molMap = new HashMap<>();
        List<?> userList = userService.queryAllUser();
        System.out.println(userList);
        molMap.put("list", userList);
        return molMap;
    }

    @RequestMapping("/sel")
    public Map<String, Object> queryUserById(Integer id) {
        Map<String, Object> molMap = new HashMap<>();
        User user = userService.queryUserById(id);
        String msg = "用户不存在";
        if(user != null) {
            molMap.put("list", user);
        }else {
            molMap.put("list", msg);
        }
        return molMap;
    }

    @RequestMapping("/add")
    public Map<String, Object> addUser(User user) {
        Map<String, Object> molMap = new HashMap<>();
        String msg = "添加用户信息失败";
        if (user != null) {//判空
            boolean flag = userService.addUser(user);
            if(flag) {
                msg = "添加用户信息成功";
            }
        }
        molMap.put("msg", msg);
        return molMap;
    }

    @RequestMapping("/modify")
    public Map<String, Object> modifyUser(User user) {
        Map<String, Object> molMap = new HashMap<>();
        String msg = "修改用户信息失败";
        if (user != null) {//判空
            boolean flag = userService.modifyUser(user);
            if(flag) {
                msg = "修改用户信息成功";
            }
        }
        molMap.put("msg", msg);
        return molMap;
    }

    @RequestMapping("/del")
    public Map<String, Object> delUser(Integer id) {
        Map<String, Object> molMap = new HashMap<>();
        String msg = "用户不存在！";
        User user = userService.queryUserById(id);
        if (user != null) {
            boolean flag = userService.delUser(id);
            if (flag) {
                msg = "删除用户信息成功";
            }else {
                msg = "删除用户信息失败";
            }
        }
        molMap.put("msg", msg);
        return molMap;
    }

}
