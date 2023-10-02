package planIT.Events;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());

        eventRepository.save(event);
        return eventRepository.findById(id);
    }

    public String deleteEvent(int id) {
        eventRepository.deleteById(id);
        return success;
    }
}
