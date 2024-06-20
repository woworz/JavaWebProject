package com.example.webproject.mapper;

import com.example.webproject.entity.Reminder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReminderMapper {

    @Select("SELECT * FROM reminders WHERE todo_id = #{todoId}")
    List<Reminder> getRemindersByTodoId(Long todoId);

    @Insert("INSERT INTO reminders (todo_id, reminder_time) VALUES (#{todo.id}, #{reminderTime})")
    void insertReminder(Reminder reminder);

    @Update("UPDATE reminders SET reminder_time = #{reminderTime} WHERE id = #{id}")
    void updateReminder(Reminder reminder);

    @Delete("DELETE FROM reminders WHERE todo_id = #{todoId}")
    void deleteRemindersByTodoId(Long todoId);
}
