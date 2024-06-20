package com.example.webproject.service;

import com.example.webproject.entity.Reminder;
import com.example.webproject.mapper.ReminderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderMapper reminderMapper;

    public List<Reminder> getRemindersByTodoId(Long todoId) {
        return reminderMapper.getRemindersByTodoId(todoId);
    }

    public void insertReminder(Reminder reminder) {
        reminderMapper.insertReminder(reminder);
    }

    public void updateReminder(Reminder reminder) {
        reminderMapper.updateReminder(reminder);
    }

    public void deleteRemindersByTodoId(Long todoId) {
        reminderMapper.deleteRemindersByTodoId(todoId);
    }
}

