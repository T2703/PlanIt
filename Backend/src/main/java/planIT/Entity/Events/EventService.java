package planIT.Entity.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.*;

/**
 *
 * @author Melani Hodge
 *
 */

// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id);
    }

    public String createEvent(String username, Event event) {
        event.setManager(userRepository.findByUsername(username));
        event.getUsers().add(userRepository.findByUsername(username));
        eventRepository.save(event);

        return success;
    }

    public Event updateEvent(int id, Event request) {
        Event event = eventRepository.findById(id);
        if (event == null)
            return null;

        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setType(request.getType());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());

        eventRepository.save(event);
        return eventRepository.findById(id);
    }

    public String addUserToEvent(String username, int eventId) {
        User user = userRepository.findByUsername(username);
        Event event = eventRepository.findById(eventId);

        user.getEvents().add(event);
        event.getUsers().add(user);

        eventRepository.save(event);

        return success;
    }

    public Set<Event> getUserEvents(String username) {
        return userRepository.findByUsername(username).getEvents();
    }

    public String deleteEvent(String username, int id) {
        eventRepository.deleteById(id);
        return success;
    }
}
