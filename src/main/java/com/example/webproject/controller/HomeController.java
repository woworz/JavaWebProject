package com.example.webproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//处理对根 URL（/）的 HTTP GET 请求，并返回一个名为 index 的视图
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "login"; // 假设有一个名为 index.html 的 Thymeleaf 模板
    }
}