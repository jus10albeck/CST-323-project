package com.gcu.taskmanager.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.model.RecurringTask;
import com.gcu.taskmanager.model.Task;
import com.gcu.taskmanager.model.Event;
import com.gcu.taskmanager.service.UserService;

import jakarta.servlet.http.HttpSession;

import com.gcu.taskmanager.service.TaskService;
import com.gcu.taskmanager.service.RecurringTaskService;
import com.gcu.taskmanager.service.EventService;

/**
 * Controller responsible for handling login-related actions.
 */
@Controller
public class LoginController
{
	
	/**
	 * Service used to handle user registration and login validation.
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Service used to retrieve and manage task data.
	 */
	@Autowired
	private TaskService taskService;
	
	/**
	 * Service used to retrieve and manage recurring task data.
	 */
	@Autowired
	private RecurringTaskService recurringTaskService;
	
	/**
	 * Service used to retrieve and manage event data.
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * Displays the login page when a user navigates to the login URL.
	 * 
	 * Initializes a new User object for Thymeleaf form binding so the
	 * login form can capture username and password input.
	 *
	 * @param model The Spring MVC model used to pass data to the view
	 * @return The login page template
	 */
	@GetMapping("/login/")
	public String showLoginPage(Model model)
	{
	    model.addAttribute("loginModel", new User());
	    return "login";
	}
	
	/**
	 * Handles login form submission.
	 * This method checks the entered username and password against the database.
	 *
	 * @param user The user object populated from the login form
	 * @param model The model used to return data to the view
	 * @return Redirects to task list if valid, otherwise reloads login page with error
	 */
	@PostMapping("/login/doLogin")
	public String doLogin(@ModelAttribute("loginModel") User user, Model model, HttpSession session)
	{
	    boolean validUser = userService.validateLogin(user.getUsername(), user.getPassword());

	    if (validUser)
	    {
	    	User loggedInUser = userService.findByUsername(user.getUsername());
	    	
	    	session.setAttribute("user", loggedInUser);
	    	
	    	return "redirect:/tasklist"; // goes to task list page
	    }

	    model.addAttribute("loginError", "Invalid username or password.");

	    return "login";
	}
	
	/**
	 * Handles user logout
	 * This method logs the user out of the application and returns the user to the login screen
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(HttpSession session)
	{
		if (session != null)
			session.invalidate();
		
		return "redirect:/";
	}
    
    /**
     * Displays the registration page.
     *
     * @param model The model used to pass data to the view
     * @return The register page template
     */
    @GetMapping("/register/")
    public String showRegisterPage(Model model)
    {
        // Provide empty object for form binding
        model.addAttribute("registrationModel", new User());

        return "register";
    }
    
    /**
     * Handles registration form submission.
     * This method receives the user information from the registration form,
     * attempts to save the user through the UserService, and redirects the
     * user back to the login page after successful registration.
     *
     * @param user The user object populated from the registration form
     * @param model The model used to send data back to the view if registration fails
     * @return Redirects to login if registration succeeds, otherwise reloads registration page
     */
    @PostMapping("/register/doRegister")
    public String doRegister(@ModelAttribute("registrationModel") User user, Model model)
    {
        boolean registered = userService.registerUser(user);

        if (registered)
        {
            model.addAttribute("loginModel", new User());
            return "login";
        }

        model.addAttribute("registrationModel", user);
        model.addAttribute("registrationError", "Username or email already exists.");

        return "register";
    }
    
    /**
	 * Displays the main task list page after a successful login.
	 * This method loads today's tasks and future tasks for the test user.
	 *
	 * @param model The model used to pass task lists to the Thymeleaf page
	 * @return The task list page template
	 */
	@GetMapping("/tasklist")
	public String showTaskList(Model model, HttpSession session)
	{
	    User user = (User) session.getAttribute("user");
	    
	    System.out.println("SESSION USER: " + (user != null ? user.getUsername() : "NULL"));

	    model.addAttribute("todayTasks", taskService.getTodayTasks(user));
	    model.addAttribute("futureTasks", taskService.getFutureTasks(user));
	    
	    return "taskList";
	}
	
	/**
	 * Handles the Add Task form submission from the task list page.
	 * This method receives task form data, connects it to the test user,
	 * saves it through the TaskService, and redirects back to the task list.
	 *
	 * @param task The task object populated from the Add Task form
	 * @return Redirects back to the task list page
	 */
	@PostMapping("/tasklist/add")
	public String addTask(@ModelAttribute Task task, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    task.setUser(user);
	    task.setCompleted(false);

	    taskService.addTask(task);

	    return "redirect:/tasklist";
	}
	
	/**
	 * Toggles the completion status of a task.
	 * If completed → becomes not completed.
	 * If not completed → becomes completed.
	 */
	@PostMapping("/tasklist/complete")
	public String toggleTask(@RequestParam Long taskId)
	{
	    taskService.toggleTaskCompletion(taskId);
	    return "redirect:/tasklist";
	}
	
	/**
	 * Displays the recurring tasks page.
	 * This method loads daily, weekly, and monthly recurring tasks for the test user.
	 *
	 * @param model The model used to pass recurring task lists to the Thymeleaf page
	 * @return The recurring tasks page template
	 */
	@GetMapping("/recurringTasks")
	public String showRecurringTasks(Model model, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    model.addAttribute("dailyTasks", recurringTaskService.getDailyRecurringTasks(user));
	    model.addAttribute("weeklyTasks", recurringTaskService.getWeeklyRecurringTasks(user));
	    model.addAttribute("monthlyTasks", recurringTaskService.getMonthlyRecurringTasks(user));

	    return "recurringTasks";
	}
	
	/**
	 * Handles the Add Daily Task form submission.
	 * This method receives recurring task form data, assigns it to the test user,
	 * sets the recurrence type to DAILY, saves it to the database, and redirects
	 * back to the recurring tasks page.
	 *
	 * @param recurringTask The recurring task populated from the Daily Task form
	 * @return Redirects back to the recurring tasks page
	 */
	@PostMapping("/recurringtasks/addDaily")
	public String addDailyRecurringTask(@ModelAttribute RecurringTask recurringTask, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    recurringTask.setUser(user);
	    recurringTask.setRecurrenceType("DAILY");
	    recurringTask.setCompleted(false);

	    recurringTaskService.addRecurringTask(recurringTask);

	    return "redirect:/recurringTasks";
	}
	
	/**
	 * Handles the Add Weekly Task form submission.
	 * This method receives recurring task form data, assigns it to the test user,
	 * sets the recurrence type to WEEKLY, stores the selected days of the week,
	 * saves it to the database, and redirects back to the recurring tasks page.
	 *
	 * @param recurringTask The recurring task populated from the Weekly Task form
	 * @param days The selected days of the week from the form checkboxes
	 * @return Redirects back to the recurring tasks page
	 */
	@PostMapping("/recurringtasks/addWeekly")
	public String addWeeklyRecurringTask(@ModelAttribute RecurringTask recurringTask,
	                                     @RequestParam(required = false) String[] days, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    recurringTask.setUser(user);
	    recurringTask.setRecurrenceType("WEEKLY");
	    recurringTask.setCompleted(false);

	    if (days != null)
	    {
	        recurringTask.setDayOfWeek(String.join(", ", days));
	    }

	    recurringTaskService.addRecurringTask(recurringTask);

	    return "redirect:/recurringTasks";
	}
	
	/**
	 * Handles the Add Monthly Task form submission.
	 * This method receives recurring task form data, assigns it to the test user,
	 * sets the recurrence type to MONTHLY, saves it to the database, and redirects
	 * back to the recurring tasks page.
	 *
	 * @param recurringTask The recurring task populated from the Monthly Task form
	 * @return Redirects back to the recurring tasks page
	 */
	@PostMapping("/recurringtasks/addMonthly")
	public String addMonthlyRecurringTask(@ModelAttribute RecurringTask recurringTask, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    recurringTask.setUser(user);
	    recurringTask.setRecurrenceType("MONTHLY");
	    recurringTask.setCompleted(false);

	    recurringTaskService.addRecurringTask(recurringTask);

	    return "redirect:/recurringTasks";
	}
	
	/**
	 * Toggles the completion status of a recurring task.
	 * This method updates the selected recurring task in the database
	 * and redirects back to the recurring tasks page.
	 *
	 * @param recurringTaskId The ID of the recurring task being toggled
	 * @return Redirects back to the recurring tasks page
	 */
	@PostMapping("/recurringtasks/complete")
	public String toggleRecurringTask(@RequestParam Long recurringTaskId)
	{
	    recurringTaskService.toggleRecurringTaskCompletion(recurringTaskId);

	    return "redirect:/recurringTasks";
	}
	
	/**
	 * Displays the events page.
	 * This method loads all events that belong to the test user.
	 *
	 * @param model The model used to pass event data to the Thymeleaf page
	 * @return The events page template
	 */
	@GetMapping("/events")
	public String showEvents(Model model, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    model.addAttribute("events", eventService.getAllEventsByUser(user));

	    return "events";
	}
	
	/**
	 * Handles adding a new event from the Events page.
	 *
	 * @param name The name of the event
	 * @param dateTime The date and time of the event
	 * @param eventCategory The selected event category
	 * @param description The event description
	 * @return Redirects back to the events page
	 */
	@PostMapping("/events/add")
	public String addEvent(
	        @RequestParam("name") String name,
	        @RequestParam("dateTime") LocalDateTime dateTime,
	        @RequestParam("eventCategory") String eventCategory,
	        @RequestParam("description") String description, HttpSession session)
	{
		User user = (User) session.getAttribute("user");

	    Event event = new Event();

	    event.setName(name);
	    event.setDateTime(dateTime);
	    event.setEventCategory(eventCategory);
	    event.setDescription(description);
	    event.setUser(user);

	    eventService.addEvent(event);

	    return "redirect:/events";
	}
}
