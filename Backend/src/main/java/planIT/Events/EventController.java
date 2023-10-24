package planIT.Events;

import java.util.List;

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
    @PostMapping(path = "/events")
    public String createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // POST method - adds an event to the database.
    @PostMapping(path = "/users/{userId}/events/{eventId}")
    public String addUserToEvent(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.addUserToEvent(userId, eventId);
    }

    // PUT method - updates an event in the database.
    @PutMapping(path = "/events/{id}")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // DELETE method - deletes an event from the database.
    @DeleteMapping(path = "/events/{id}")
    public String deleteEvent(@PathVariable int id) {
        return eventService.deleteEvent(id);
    }
}

