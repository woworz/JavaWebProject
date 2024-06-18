package com.example.webproject.controller;

import com.example.webproject.entity.Category;
import com.example.webproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 显示分类
     * @param model
     * @return String
     */
    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    /**
     * 新建分类
     * @param name 分类名
     * @return String
     */
    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.insertCategory(category);
        return "redirect:/categories";
    }
}
