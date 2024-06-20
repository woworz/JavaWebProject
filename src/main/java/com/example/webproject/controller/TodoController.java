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
        for (Todo todo : todos) {
            // 确保每个待办事项都有正确的分类加载
            if (todo.getCategory() == null) {
                todo.setCategory(categoryService.getCategoryById(DEFAULT_CATEGORY_ID));
            }
        }
        model.addAttribute("todos", todos);
        model.addAttribute("userId", loggedInUser.getId());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "todo";
    }


    /**
     * 添加TODO
     * @param title 标题
     * @param description 具体描述
     * @param reminderTime 提醒时间
     * @param categoryId 分类ID
     * @param completed 完成标志
     * @param session
     * @return String
     */
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

    /**
     * 更新TODO
     * @param id TODO_id
     * @param title 标题
     * @param description 描述
     * @param completed 完成标志
     * @param reminderTime 提醒时间
     * @param categoryId 分类ID
     * @param session
     * @return String
     */
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
        List<Reminder> reminders = reminderService.getRemindersByTodoId(id);
        if (reminderTime != null) {
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
