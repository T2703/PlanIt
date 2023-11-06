package planIT.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import planIT.Users.UserRepository;

@RestController
public class UserEventController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventService userEventService;

    @GetMapping("/user_events")
    public List<UserEvent> getUserEvent() {
        return userEventService.getAllRelated();
    }

    @GetMapping("user_events/{username}")
    public int[] getUserEventByUsername(String username) {
        int userId = userRepository.findByUsername(username).getId();

        return userEventService.getUserEventsById(userId);
    }
}

