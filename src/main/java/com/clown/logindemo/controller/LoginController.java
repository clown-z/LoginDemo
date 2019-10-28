package com.clown.logindemo.controller;

import com.clown.logindemo.entity.User;
import com.clown.logindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping("")
    public String login() {
        return "login";
    }

    @RequestMapping("/checkLogin")
    public ModelAndView checkLogin() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("requestMsg", "12s");
        return view;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> checkUser(String username, String password) {
        Map<String, Object> molMap = new HashMap<>();
        System.out.println(username+ password);
        User user = userService.queryUserByName(username);
        String msg = "用户不存在";
        String status = "0";
        if(user != null){
            if (user.getPassword().equals(password)) {
                msg = username + " login success";
                status = "1";
            }else {
                msg = "密码错误！";
            }
        }
        molMap.put("msg", msg);
        molMap.put("status", status);
        return molMap;
    }



    @RequestMapping("/list")
    public String list(Model model){
        List<User> userList = userService.queryAllUser();
        model.addAttribute("list", userList);
        return "list";
    }
}
