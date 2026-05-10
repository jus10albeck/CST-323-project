package com.gcu.taskmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcu.taskmanager.model.Event;
import com.gcu.taskmanager.model.User;

/**
 * Repository interface for performing database operations on Event entities.
 * This interface extends JpaRepository so that standard CRUD operations
 * are automatically provided by Spring Data JPA.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>
{
    /**
     * Finds all events that belong to a specific user.
     *
     * @param user The user whose events should be returned
     * @return A list of events belonging to the given user
     */
    public List<Event> findByUser(User user);

    /**
     * Finds all events for a specific user by event category.
     *
     * @param user The user whose events should be returned
     * @param eventCategory The category to search for
     * @return A list of events matching the given user and category
     */
    public List<Event> findByUserAndEventCategory(User user, String eventCategory);

    /**
     * Finds all events for a specific user on or after a given date.
     *
     * @param user The user whose events should be returned
     * @param eventDate The starting event date
     * @return A list of events on or after the given date
     */
    public List<Event> findByUserAndEventDateGreaterThanEqual(User user, LocalDate eventDate);
}