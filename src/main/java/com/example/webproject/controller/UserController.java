package com.example.webproject.controller;

import com.example.webproject.entity.User;
import com.example.webproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/management")
    public String showUserManagement(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getId() != 1) {
            return "redirect:/login";
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userManagement";
    }

    @GetMapping("/details/{id}")
    public String showUserDetails(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getId() != 1) {
            return "redirect:/login";
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "userDetails";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String username,
                             @RequestParam String password) {
        User user = userService.getUserById(id);
        user.setUsername(username);
        user.setPassword(password); // 直接设置密码
        userService.updateUser(user);
        return "redirect:/user/management";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/user/management";
    }
}
