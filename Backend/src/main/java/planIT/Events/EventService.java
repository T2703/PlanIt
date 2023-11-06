package planIT.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Users.*;

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

    @Autowired
    private UserEventRepository userEventRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id);
    }

    public String createEvent(Event event) {
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
        event.setManager(request.getManager());

        eventRepository.save(event);
        return eventRepository.findById(id);
    }

    public String createEventWithUser(String username, Event event) {
        UserEvent related = new UserEvent(userRepository.findByUsername(username).getId(), eventRepository.save(event).getId());

        userEventRepository.save(related);

        return success;
    }

    public String addUserToEvent(int userId, int eventId) {
        User user = userRepository.findById(userId);
        Event event = eventRepository.findById(eventId);

        user.getEvents().add(event);
        event.getUsers().add(user);

        eventRepository.save(event);

        return success;
    }

    public List<Event> findEventsByUser(String username) {
        List<Event> events = new ArrayList<>();
        int[] eventIds = userEventRepository.findEventIdsByUserId(userRepository.findByUsername(username).getId());
        for (int i = 0; i < eventIds.length; i++) {
            events.add(eventRepository.findById(eventIds[i]));
        }
        return events;
    }

    public String deleteEvent(String username, int id) {
        userEventRepository.deleteByUserIdAndEventId(userRepository.findByUsername(username).getId(), id);
        eventRepository.deleteById(id);
        return success;
    }
}
