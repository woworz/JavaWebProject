package com.example.webproject.service;

import com.example.webproject.entity.Todo;
import com.example.webproject.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoMapper todoMapper;
    @Autowired
    private ReminderService reminderService;

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

    @Transactional
    public void deleteTodoWithReminders(Long todoId) {
        // 先删除相关的 reminders
        reminderService.deleteRemindersByTodoId(todoId);
        // 然后删除 todo
        todoMapper.deleteTodo(todoId);
    }
}
