package com.gcu.taskmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.taskmanager.model.Event;
import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.repository.EventRepository;

/**
 * Service class that contains the business logic for Event operations.
 * This class handles creating, retrieving, filtering, and deleting events.
 */
@Service
public class EventService
{
    /**
     * Repository used to perform database operations for Event entities.
     */
    @Autowired
    private EventRepository eventRepository;

    /**
     * Saves an event to the database.
     *
     * @param event The event to save
     * @return The saved Event object
     */
    public Event addEvent(Event event)
    {
        return eventRepository.save(event);
    }

    /**
     * Retrieves all events that belong to a specific user.
     *
     * @param user The user whose events should be returned
     * @return A list of all events for the user
     */
    public List<Event> getAllEventsByUser(User user)
    {
        return eventRepository.findByUser(user);
    }

    /**
     * Retrieves all events for a specific user by category.
     *
     * @param user The user whose events should be returned
     * @param eventCategory The category to filter by
     * @return A list of events matching the given category
     */
    public List<Event> getEventsByCategory(User user, String eventCategory)
    {
        return eventRepository.findByUserAndEventCategory(user, eventCategory);
    }

    /**
     * Retrieves all upcoming events for a specific user.
     *
     * @param user The user whose upcoming events should be returned
     * @return A list of upcoming events
     */
    public List<Event> getUpcomingEvents(User user)
    {
        return eventRepository.findByUserAndEventDateGreaterThanEqual(user, LocalDate.now());
    }

    /**
     * Deletes an event by its ID if it exists.
     *
     * @param eventId The ID of the event to delete
     * @return true if the event was found and deleted, false otherwise
     */
    public boolean deleteEvent(Long eventId)
    {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent())
        {
            eventRepository.deleteById(eventId);
            return true;
        }

        return false;
    }

    /**
     * Finds an event by its ID.
     *
     * @param eventId The ID of the event to search for
     * @return The matching Event object, or null if not found
     */
    public Event findEventById(Long eventId)
    {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent())
        {
            return eventOptional.get();
        }

        return null;
    }
}