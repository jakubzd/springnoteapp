package com.example.springproject.controller;

import com.example.springproject.category.CategoryService;
import com.example.springproject.note.Note;
import com.example.springproject.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "/welcome/home";
    }

    @GetMapping("/login")
    public String login() {
        return "/welcome/login";
    }

    @PostMapping("/login")
    public String logon() {
        return "welcome/home";
    }

}
