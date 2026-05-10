package com.gcu.taskmanager.config;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gcu.taskmanager.model.Event;
import com.gcu.taskmanager.model.RecurringTask;
import com.gcu.taskmanager.model.Task;
import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.service.EventService;
import com.gcu.taskmanager.service.RecurringTaskService;
import com.gcu.taskmanager.service.TaskService;
import com.gcu.taskmanager.service.UserService;

/**
 * Temporary data loader used to insert sample records into the database
 * when the application starts.
 * 
 * This class is useful during early development because it confirms that
 * the entity, repository, service, and database layers are working together.
 * 
 * This class can be removed later before final deployment if sample data
 * is no longer needed.
 */
@Component
public class DataLoader implements CommandLineRunner
{
    /**
     * Service used to register and retrieve users.
     */
    @Autowired
    private UserService userService;

    /**
     * Service used to create and manage standard tasks.
     */
    @Autowired
    private TaskService taskService;

    /**
     * Service used to create and manage recurring tasks.
     */
    @Autowired
    private RecurringTaskService recurringTaskService;

    /**
     * Service used to create and manage events.
     */
    @Autowired
    private EventService eventService;

    /**
     * Runs automatically when the Spring Boot application starts.
     * This method creates one sample user and several sample items.
     *
     * @param args Command-line arguments passed to the application
     */
    @Override
    public void run(String... args)
    {
        User existingUser = userService.findByUsername("testuser");

        if (existingUser == null)
        {
            User user = new User(
                "Test",
                "User",
                "testuser@email.com",
                "555-555-5555",
                "Email",
                "testuser",
                "password"
            );

            userService.registerUser(user);

            Task task = new Task(
                "Finish Milestone 2 Backend",
                "Complete backend service and database setup.",
                LocalDate.now(),
                LocalTime.of(18, 0),
                1,
                "Home",
                false,
                user
            );

            taskService.addTask(task);

            RecurringTask recurringTask = new RecurringTask(
                "Check Planner",
                "Review daily planner and task list.",
                "DAILY",
                null,
                null,
                LocalTime.of(9, 0),
                2,
                "Home",
                false,
                user
            );

            recurringTaskService.addRecurringTask(recurringTask);

            Event event = new Event(
                "Project Meeting",
                "Meet with group member to review progress.",
                "APPOINTMENT",
                LocalDate.now().plusDays(2),
                LocalTime.of(14, 30),
                "Online",
                user
            );

            eventService.addEvent(event);
        }
    }
}