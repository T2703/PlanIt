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

@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/events")
    List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    @GetMapping(path = "/events/{id}")
    Event getEventById(@PathVariable int id){
        return eventRepository.findById(id);
    }

    @PostMapping(path = "/events")
    String createEvent(@RequestBody Event Event){
        if (Event == null)
            return failure;
        eventRepository.save(Event);
        return success;
    }

    @PutMapping(path = "/events/{id}")
    Event updateEvent(@PathVariable int id, @RequestBody Event request){
        Event event = eventRepository.findById(id);
        if(event == null)
            return null;
        eventRepository.save(request);
        return eventRepository.findById(id);
    }

    @DeleteMapping(path = "/events/{id}")
    String deleteEvent(@PathVariable int id){
        eventRepository.deleteById(id);
        return success;
    }

}
