package com.gcu.taskmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcu.taskmanager.model.Task;
import com.gcu.taskmanager.model.User;

/**
 * Repository interface for performing database operations on Task entities.
 * This interface extends JpaRepository so that standard CRUD operations
 * are automatically provided by Spring Data JPA.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>
{
    /**
     * Finds all tasks that belong to a specific user.
     *
     * @param user The user whose tasks should be returned
     * @return A list of tasks belonging to the given user
     */
    public List<Task> findByUser(User user);

    /**
     * Finds all tasks for a specific user that are due on a specific date.
     *
     * @param user The user whose tasks should be returned
     * @param dueDate The due date to search for
     * @return A list of tasks for the user on the given date
     */
    public List<Task> findByUserAndDueDate(User user, LocalDate dueDate);

    /**
     * Finds all tasks for a specific user with a due date after a given date.
     *
     * @param user The user whose tasks should be returned
     * @param dueDate The date used for comparison
     * @return A list of future tasks for the user
     */
    public List<Task> findByUserAndDueDateAfter(User user, LocalDate dueDate);
}