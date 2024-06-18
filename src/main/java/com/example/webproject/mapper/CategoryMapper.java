package com.example.webproject.mapper;

import com.example.webproject.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Insert("INSERT INTO categories(name) VALUES(#{name})")
    void insertCategory(Category category);

    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category getCategoryById(Long id);
}
