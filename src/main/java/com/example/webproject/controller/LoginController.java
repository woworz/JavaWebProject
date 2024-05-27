package com.example.webproject.controller;

import com.example.webproject.entity.User;
import com.example.webproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "user"; // 登录成功后跳转到用户页面
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }
}
