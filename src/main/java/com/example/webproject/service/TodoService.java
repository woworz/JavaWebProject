package com.example.webproject.service;

import com.example.webproject.entity.Todo;
import com.example.webproject.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoMapper todoMapper;

    public List<Todo> getTodosByUserId(Long id) {
        return todoMapper.getTodosByUserId(id);
    }

    /**
     * 新建todo
     */
    public void insertTodo(Todo todo) {
        todoMapper.insertTodo(todo);
    }

    /**
     * 更新todo
     */
    public void updateTodo(Todo todo) {
        todoMapper.updateTodo(todo);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteTodo(Long id) {
        todoMapper.deleteTodo(id);
    }

    public Todo getTodoById(Long id) {
        return todoMapper.getTodoById(id);
    }
}
