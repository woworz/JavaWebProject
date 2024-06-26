package com.example.webproject.controller;

import com.example.webproject.entity.Category;
import com.example.webproject.entity.Reminder;
import com.example.webproject.entity.Todo;
import com.example.webproject.entity.User;
import com.example.webproject.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    private static final Long DEFAULT_CATEGORY_ID = 1L; // 默认分类 ID

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
        model.addAttribute("user", loggedInUser);
        return "todo";
    }

    @PostMapping("/add")
    public String addTodo(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderTime,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(defaultValue = "false") boolean completed,
                          HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);
        todo.setUser(loggedInUser);

        // 使用默认分类 ID
        if (categoryId == null) {
            categoryId = DEFAULT_CATEGORY_ID;
        }
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

    @PostMapping("/updateCompleted")
    public String updateCompleted(@RequestParam Long id, @RequestParam(required = false, defaultValue = "false") boolean completed) {
        Todo todo = todoService.getTodoById(id);
        todo.setCompleted(completed);
        todoService.updateTodo(todo);
        return "redirect:/todo";
    }

    @GetMapping("/details/{id}")
    public String showTodoDetails(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "todoDetails";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id,
                             @RequestParam String title,
                             @RequestParam String description,
                             @RequestParam boolean completed,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderTime,
                             @RequestParam(required = false) Long categoryId,
                             HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = todoService.getTodoById(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);

        // 使用默认分类 ID
        if (categoryId == null) {
            categoryId = DEFAULT_CATEGORY_ID;
        }
        Category category = categoryService.getCategoryById(categoryId);
        todo.setCategory(category);

        todoService.updateTodo(todo);

        // 更新提醒时间
        if (reminderTime != null) {
            List<Reminder> reminders = reminderService.getRemindersByTodoId(id);
            if (!reminders.isEmpty()) {
                Reminder reminder = reminders.get(0);
                reminder.setReminderTime(reminderTime);
                reminderService.updateReminder(reminder);
            } else {
                Reminder reminder = new Reminder();
                reminder.setTodo(todo);
                reminder.setReminderTime(reminderTime);
                reminderService.insertReminder(reminder);
            }
        } else {
            reminderService.deleteRemindersByTodoId(id);
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
