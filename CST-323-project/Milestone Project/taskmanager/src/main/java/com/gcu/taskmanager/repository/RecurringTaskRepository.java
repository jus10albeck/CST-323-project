package com.gcu.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcu.taskmanager.model.RecurringTask;
import com.gcu.taskmanager.model.User;

/**
 * Repository interface for performing database operations on RecurringTask entities.
 * This interface extends JpaRepository so that standard CRUD operations
 * are automatically provided by Spring Data JPA.
 */
@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, Long>
{
    /**
     * Finds all recurring tasks that belong to a specific user.
     *
     * @param user The user whose recurring tasks should be returned
     * @return A list of recurring tasks belonging to the given user
     */
    public List<RecurringTask> findByUser(User user);

    /**
     * Finds all recurring tasks for a specific user with a given recurrence type.
     *
     * @param user The user whose recurring tasks should be returned
     * @param recurrenceType The recurrence type to search for
     * @return A list of recurring tasks for the given user and recurrence type
     */
    public List<RecurringTask> findByUserAndRecurrenceType(User user, String recurrenceType);
}