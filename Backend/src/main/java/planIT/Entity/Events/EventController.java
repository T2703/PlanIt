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
 *
 * @author Melani Hodge
 *
 */

@RestController
public class EventController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private EventService eventService;

    // GET method - retreives all events from the database.
    @GetMapping(path = "/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET method - retreives a event from the database.
    @GetMapping(path = "/events/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    // POST method - adds an event to the database.
    @PostMapping(path = "users/{username}/events")
    public String createEvent(@PathVariable String username, @RequestBody Event event) {
        return eventService.createEvent(username, event);
    }

    @PostMapping(path = "/users/{username}/events/{eventId}")
    public String addUserToEvent(@PathVariable String username, @PathVariable int eventId) {
        return eventService.addUserToEvent(username, eventId);
    }

    // GET method - adds an event to the database.
    @GetMapping(path = "/users/{username}/events")
    public Set<Event> addUserToEvent(@PathVariable String username) {
        return eventService.getUserEvents(username);
    }

    // PUT method - updates an event in the database.
    @PutMapping(path = "/events/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // DELETE method - deletes an event from the database.
    @DeleteMapping(path = "users/{username}/events/{id}")
    public String deleteEvent(@PathVariable String username, @PathVariable int id) {
        return eventService.deleteEvent(username, id);
    }
}

