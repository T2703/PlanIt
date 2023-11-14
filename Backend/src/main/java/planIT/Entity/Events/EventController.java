package planIT.Entity.Events;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for event entity
 * @author Melani Hodge
 *
 */
@RestController
public class EventController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private EventService eventService;

    // GET method - retreives all events from the database.
    /**
     * Gets all of an event's users and returns them as a List
     * @return events list
     */
    @GetMapping(path = "/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET method - retreives a event from the database.
    /**
     * Gets an event from repository by its id number
     * @param id id number of target event
     * @return event
     */
    @GetMapping(path = "/events/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    // POST method - adds an event to the database.
    /**
     * Creates a new event and attaches it to a user
     * @param username username of target user
     * @param event event to be created
     * @return success
     */
    @PostMapping(path = "users/{username}/events")
    public String createEvent(@PathVariable String username, @RequestBody Event event) {
        return eventService.createEvent(username, event);
    }

    /**
     * Adds a preexisting user to a preexisting event
     * @param username username of target user
     * @param eventId id number of target event
     * @return success
     */
    @PostMapping(path = "/users/{username}/events/{eventId}")
    public String addUserToEvent(@PathVariable String username, @PathVariable int eventId) {
        return eventService.addUserToEvent(username, eventId);
    }

    // GET method - adds an event to the database.
    /**
     * Returns all events a particular user has as a set.
     * @param username username of target user
     * @return Events
     */
    @GetMapping(path = "/users/{username}/events")
    public Set<Event> getUserEvents(@PathVariable String username) {
        return eventService.getUserEvents(username);
    }

    // PUT method - updates an event in the database.
    /**
     * Updates an event in the repository
     * @param id id number of event to be updated
     * @param event event object with updated details
     * @return event
     */
    @PutMapping(path = "/events/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // DELETE method - deletes an event from the database.
    /**
     * Deletes am event from the repository
     * @param username ...
     * @param id id number of target event
     * @return success
     */
    @DeleteMapping(path = "users/{username}/events/{id}")
    public String deleteEvent(@PathVariable String username, @PathVariable int id) {
        return eventService.deleteEvent(username, id);
    }
}

