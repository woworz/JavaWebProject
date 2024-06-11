package com.example.webproject.controller;

import com.example.webproject.entity.Todo;
import com.example.webproject.entity.User;
import com.example.webproject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

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

    @PostMapping("/add")
    public String addTodo(@RequestParam String title, @RequestParam String description, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(false);
        todo.setUser(loggedInUser);
        todoService.insertTodo(todo);
        return "redirect:/todo";
    }

    @PostMapping("/update")
    public String updateTodo(@RequestParam Long id, @RequestParam String title, @RequestParam String description, @RequestParam boolean completed, HttpSession session) {
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
