package planIT.Entity.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.*;



// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
/**
 * Service class for the event entity
 * @author Melani Hodge
 *
 */
@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Returns all events from repository as List
     * @return Event List
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Returns an event from the repository by its user id
     * @param id id number of the target event
     * @return event
     */
    public Event getEventById(int id) {
        return eventRepository.findById(id);
    }

    /**
     * Saves a new event to the repository and attaches it to a user
     * @param username username of target user
     * @param event newly created event
     * @return success
     */
    public String createEvent(String username, Event event) {
        event.setManager(userRepository.findByUsername(username));
        event.getUsers().add(userRepository.findByUsername(username));
        eventRepository.save(event);

        return success;
    }

    /**
     * Updates a preexisting event in the repository
     * @param id id of target event
     * @param request event object with the info to update
     * @return event
     */
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

    /**
     * Adds a preexisting user to a preexisting event
     * @param username username of target user
     * @param eventId id number of target event
     * @return success
     */
    public String addUserToEvent(String username, int eventId) {
        User user = userRepository.findByUsername(username);
        Event event = eventRepository.findById(eventId);

        user.getEvents().add(event);
        event.getUsers().add(user);

        eventRepository.save(event);

        return success;
    }

    /**
     * Gets all events associated with a particular user and returns them as a Set.
     * @param username username of target user
     * @return event set
     */
    public Set<Event> getUserEvents(String username) {
        return userRepository.findByUsername(username).getEvents();
    }

    /**
     * Deletes an event from the repository
     * @param username   ...
     * @param id id number of target event
     * @return success
     */
    public String deleteEvent(String username, int id) {
        eventRepository.deleteById(id);
        return success;
    }
}
