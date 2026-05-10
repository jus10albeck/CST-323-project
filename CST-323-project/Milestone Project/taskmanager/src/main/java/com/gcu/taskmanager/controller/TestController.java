package com.gcu.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gcu.taskmanager.model.User;

/**
 * Controller used to handle basic page navigation for the task manager application.
 * This controller currently routes the root URL to the login page.
 */
@Controller
public class TestController
{
    /**
     * Displays the login page when the user visits the root URL.
     * 
     * The login page expects a model object named "loginModel" so that
     * Thymeleaf form fields can bind to username and password values.
     *
     * @param model The Spring MVC model used to pass data to the view
     * @return The login page template
     */
    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("loginModel", new User());

        return "login";
    }
}