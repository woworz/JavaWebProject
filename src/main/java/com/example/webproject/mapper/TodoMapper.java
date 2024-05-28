package com.example.webproject.mapper;

import com.example.webproject.entity.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TodoMapper {
    @Select("SELECT * FROM todos WHERE user_id = #{userId}")
    List<Todo> getTodosByUserId(Long userId);

    @Insert("INSERT INTO todos(title, description, completed, user_id) VALUES(#{title}, #{description}, #{completed}, #{user.id})")
    void insertTodo(Todo todo);

    @Update("UPDATE todos SET title = #{title}, description = #{description}, completed = #{completed} WHERE id = #{id}")
    void updateTodo(Todo todo);

    @Delete("DELETE FROM todos WHERE id = #{id}")
    void deleteTodo(Long id);
}
