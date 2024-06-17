package com.example.webproject.mapper;

import com.example.webproject.entity.Reminder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReminderMapper {

    List<Reminder> getRemindersByTodoId(@Param("todoId") Long todoId);

    void insertReminder(Reminder reminder);
}
