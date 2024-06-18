package com.example.webproject.service;

import com.example.webproject.entity.Category;
import com.example.webproject.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    public Category getCategoryById(Long id) {
        return categoryMapper.getCategoryById(id);
    }
}
