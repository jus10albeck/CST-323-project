package com.gcu.taskmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.taskmanager.model.Task;
import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.repository.TaskRepository;

/**
 * Service class that contains the business logic for Task operations.
 * This class handles creating, retrieving, updating, and deleting tasks.
 */
@Service
public class TaskService
{
    /**
     * Repository used to perform database operations for Task entities.
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Saves a task to the database.
     *
     * @param task The task to save
     * @return The saved Task object
     */
    public Task addTask(Task task)
    {
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks that belong to a specific user.
     *
     * @param user The user whose tasks should be returned
     * @return A list of all tasks for the user
     */
    public List<Task> getAllTasksByUser(User user)
    {
        return taskRepository.findByUser(user);
    }

    /**
     * Retrieves all tasks for a specific user that are due today.
     *
     * @param user The user whose tasks should be returned
     * @return A list of tasks due today
     */
    public List<Task> getTodayTasks(User user)
    {
        return taskRepository.findByUserAndDueDate(user, LocalDate.now());
    }

    /**
     * Retrieves all future tasks for a specific user.
     *
     * @param user The user whose tasks should be returned
     * @return A list of future tasks
     */
    public List<Task> getFutureTasks(User user)
    {
        return taskRepository.findByUserAndDueDateAfter(user, LocalDate.now());
    }

    /**
     * Marks a task as completed if it exists.
     *
     * @param taskId The ID of the task to mark as completed
     * @return true if the task was found and updated, false otherwise
     */
    public boolean markTaskCompleted(Long taskId)
    {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent())
        {
            Task task = taskOptional.get();
            task.setCompleted(true);
            taskRepository.save(task);
            return true;
        }

        return false;
    }

    /**
     * Deletes a task by its ID if it exists.
     *
     * @param taskId The ID of the task to delete
     * @return true if the task was found and deleted, false otherwise
     */
    public boolean deleteTask(Long taskId)
    {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent())
        {
            taskRepository.deleteById(taskId);
            return true;
        }

        return false;
    }

    /**
     * Finds a task by its ID.
     *
     * @param taskId The ID of the task to search for
     * @return The matching Task object, or null if not found
     */
    public Task findTaskById(Long taskId)
    {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent())
        {
            return taskOptional.get();
        }

        return null;
    }
    
    /**
     * Toggles a task's completed status.
     *
     * @param taskId The ID of the task
     */
    public void toggleTaskCompletion(Long taskId)
    {
        Task task = taskRepository.findById(taskId).orElse(null);

        if (task != null)
        {
            task.setCompleted(!task.isCompleted()); // flips true ↔ false
            taskRepository.save(task);
        }
    }
}