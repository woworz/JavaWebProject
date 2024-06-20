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

    public Todo getTodoById(Long id) {
        return todoMapper.getTodoById(id);
    }

    public List<Todo> getTodosByUserId(Long userId) {
        return todoMapper.getTodosByUserId(userId);
    }

    public void insertTodo(Todo todo) {
        todoMapper.insertTodo(todo);
    }

    public void updateTodo(Todo todo) {
        todoMapper.updateTodo(todo);
    }

    public void deleteTodo(Long id) {
        todoMapper.deleteTodo(id);
    }
}
