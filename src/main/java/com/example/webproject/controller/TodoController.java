package com.example.webproject.controller;

import com.example.webproject.entity.Reminder;
import com.example.webproject.entity.Todo;
import com.example.webproject.entity.User;
import com.example.webproject.service.ReminderService;
import com.example.webproject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ReminderService reminderService;

    @GetMapping
    public String showTodoList(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Todo> todos = todoService.getTodosByUserId(loggedInUser.getId());
        model.addAttribute("todos", todos);
        model.addAttribute("userId", loggedInUser.getId());
        return "todo";
    }

    /**
     * 添加待办事项
     * @param title 标题
     * @param description 具体内容
     * @param reminderTime 提醒时间
     * @param session
     * @return String
     */
    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String description, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderTime, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) { //未登录返回登录页
            return "redirect:/login";
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(false);
        todo.setUser(loggedInUser);
        todoService.insertTodo(todo);

        if (reminderTime != null) { //添加提醒
            Reminder reminder = new Reminder();
            reminder.setTodo(todo);
            reminder.setReminderTime(reminderTime);
            reminderService.insertReminder(reminder);
        }

        return "redirect:/todo";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title, @RequestParam String description, @RequestParam boolean completed, @RequestParam(required = false) LocalDateTime reminderTime, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);
        todo.setUser(loggedInUser);
        todoService.updateTodo(todo);

        if (reminderTime != null) {
            Reminder reminder = new Reminder();
            reminder.setTodo(todo);
            reminder.setReminderTime(reminderTime);
            reminderService.insertReminder(reminder);
        }

        return "redirect:/todo";
    }

    @PostMapping("/delete")
    public String deleteTodo(@RequestParam Long id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        todoService.deleteTodo(id);
        return "redirect:/todo";
    }
}
