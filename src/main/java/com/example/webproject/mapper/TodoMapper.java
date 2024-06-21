package com.example.webproject.mapper;

import com.example.webproject.entity.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoMapper {

    @Select("SELECT t.*, c.id AS category_id, c.name AS category_name FROM todos t LEFT JOIN categories c ON t.category_id = c.id WHERE t.id = #{id}")
    Todo getTodoById(Long id);

    @Select("SELECT t.*, c.id AS category_id, c.name AS category_name FROM todos t LEFT JOIN categories c ON t.category_id = c.id WHERE t.user_id = #{userId}")
    List<Todo> getTodosByUserId(Long userId);

    @Insert("INSERT INTO todos (title, description, completed, user_id, category_id) VALUES (#{title}, #{description}, #{completed}, #{user.id}, #{category.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTodo(Todo todo);

    @Update("UPDATE todos SET title = #{title}, description = #{description}, completed = #{completed}, category_id = #{category.id} WHERE id = #{id}")
    void updateTodo(Todo todo);

    @Delete("DELETE FROM todos WHERE id = #{id}")
    void deleteTodo(Long id);
}
