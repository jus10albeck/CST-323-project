package com.gcu.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.taskmanager.model.RecurringTask;
import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.repository.RecurringTaskRepository;

/**
 * Service class that contains the business logic for RecurringTask operations.
 * This class handles creating, retrieving, updating, and deleting recurring tasks.
 */
@Service
public class RecurringTaskService
{
    /**
     * Repository used to perform database operations for RecurringTask entities.
     */
    @Autowired
    private RecurringTaskRepository recurringTaskRepository;

    /**
     * Saves a recurring task to the database.
     *
     * @param recurringTask The recurring task to save
     * @return The saved RecurringTask object
     */
    public RecurringTask addRecurringTask(RecurringTask recurringTask)
    {
        return recurringTaskRepository.save(recurringTask);
    }

    /**
     * Retrieves all recurring tasks that belong to a specific user.
     *
     * @param user The user whose recurring tasks should be returned
     * @return A list of all recurring tasks for the user
     */
    public List<RecurringTask> getAllRecurringTasksByUser(User user)
    {
        return recurringTaskRepository.findByUser(user);
    }

    /**
     * Retrieves all daily recurring tasks for a specific user.
     *
     * @param user The user whose daily recurring tasks should be returned
     * @return A list of daily recurring tasks
     */
    public List<RecurringTask> getDailyRecurringTasks(User user)
    {
        return recurringTaskRepository.findByUserAndRecurrenceType(user, "DAILY");
    }

    /**
     * Retrieves all weekly recurring tasks for a specific user.
     *
     * @param user The user whose weekly recurring tasks should be returned
     * @return A list of weekly recurring tasks
     */
    public List<RecurringTask> getWeeklyRecurringTasks(User user)
    {
        return recurringTaskRepository.findByUserAndRecurrenceType(user, "WEEKLY");
    }

    /**
     * Retrieves all monthly recurring tasks for a specific user.
     *
     * @param user The user whose monthly recurring tasks should be returned
     * @return A list of monthly recurring tasks
     */
    public List<RecurringTask> getMonthlyRecurringTasks(User user)
    {
        return recurringTaskRepository.findByUserAndRecurrenceType(user, "MONTHLY");
    }

    /**
     * Marks a recurring task as completed if it exists.
     *
     * @param recurringTaskId The ID of the recurring task to mark as completed
     * @return true if the recurring task was found and updated, false otherwise
     */
    public boolean markRecurringTaskCompleted(Long recurringTaskId)
    {
        Optional<RecurringTask> recurringTaskOptional = recurringTaskRepository.findById(recurringTaskId);

        if (recurringTaskOptional.isPresent())
        {
            RecurringTask recurringTask = recurringTaskOptional.get();
            recurringTask.setCompleted(true);
            recurringTaskRepository.save(recurringTask);
            return true;
        }

        return false;
    }

    /**
     * Deletes a recurring task by its ID if it exists.
     *
     * @param recurringTaskId The ID of the recurring task to delete
     * @return true if the recurring task was found and deleted, false otherwise
     */
    public boolean deleteRecurringTask(Long recurringTaskId)
    {
        Optional<RecurringTask> recurringTaskOptional = recurringTaskRepository.findById(recurringTaskId);

        if (recurringTaskOptional.isPresent())
        {
            recurringTaskRepository.deleteById(recurringTaskId);
            return true;
        }

        return false;
    }

    /**
     * Finds a recurring task by its ID.
     *
     * @param recurringTaskId The ID of the recurring task to search for
     * @return The matching RecurringTask object, or null if not found
     */
    public RecurringTask findRecurringTaskById(Long recurringTaskId)
    {
        Optional<RecurringTask> recurringTaskOptional = recurringTaskRepository.findById(recurringTaskId);

        if (recurringTaskOptional.isPresent())
        {
            return recurringTaskOptional.get();
        }

        return null;
    }
    
    /**
     * Toggles a recurring task's completed status.
     * If the task is completed, it becomes incomplete.
     * If the task is incomplete, it becomes completed.
     *
     * @param recurringTaskId The ID of the recurring task to update
     */
    public void toggleRecurringTaskCompletion(Long recurringTaskId)
    {
        RecurringTask recurringTask = recurringTaskRepository.findById(recurringTaskId).orElse(null);

        if (recurringTask != null)
        {
            recurringTask.setCompleted(!recurringTask.isCompleted());
            recurringTaskRepository.save(recurringTask);
        }
    }
}
