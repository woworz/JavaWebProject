package com.example.webproject.controller;

import com.example.webproject.entity.Category;
import com.example.webproject.entity.Reminder;
import com.example.webproject.entity.Todo;
import com.example.webproject.entity.User;
import com.example.webproject.service.CategoryService;
import com.example.webproject.service.ReminderService;
import com.example.webproject.service.TodoService;
import com.example.webproject.service.UserService;
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
    private ReminderService reminderService; //提醒

    @Autowired
    private CategoryService categoryService; //分类

    @GetMapping
    public String showTodoList(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        List<Todo> todos = todoService.getTodosByUserId(loggedInUser.getId());
        model.addAttribute("todos", todos);
        model.addAttribute("userId", loggedInUser.getId());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "todo";
    }

    /**
     * 添加todo
     * @param title 标题
     * @param description 具体描述
     * @param reminderTime 提醒时间
     * @param categoryId 分类ID
     * @param session
     * @return String
     */
    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String description, @RequestParam(required = false) LocalDateTime reminderTime, @RequestParam Long categoryId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(false);
        todo.setUser(loggedInUser);

        Category category = categoryService.getCategoryById(categoryId);
        todo.setCategory(category);

        todoService.insertTodo(todo);

        if (reminderTime != null) {
            Reminder reminder = new Reminder();
            reminder.setTodo(todo);
            reminder.setReminderTime(reminderTime);
            reminderService.insertReminder(reminder);
        }

        return "redirect:/todo";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title, @RequestParam String description, @RequestParam boolean completed, @RequestParam(required = false) LocalDateTime reminderTime, @RequestParam Long categoryId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = todoService.getTodoById(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);

        Category category = categoryService.getCategoryById(categoryId);
        todo.setCategory(category);

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
