package com.gcu.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles root URL navigation.
 */
@Controller
public class HomeController
{
    /**
     * Redirects root URL to login page.
     *
     * @return redirect to login
     */
    @GetMapping("/")
    public String redirectToLogin()
    {
        return "redirect:/login/";
    }
}