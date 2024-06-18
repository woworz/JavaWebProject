package com.example.webproject.mapper;

import com.example.webproject.entity.Reminder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReminderMapper {

    @Select("SELECT * FROM reminders WHERE todo_id = #{todoId}")
    List<Reminder> getRemindersByTodoId(@Param("todoId") Long todoId);

    @Insert("INSERT INTO reminders (todo_id, reminder_time) VALUES (#{todo.id}, #{reminderTime})")
    void insertReminder(Reminder reminder);
}
