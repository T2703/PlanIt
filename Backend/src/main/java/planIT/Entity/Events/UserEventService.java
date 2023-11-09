package planIT.Entity.Events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserEventService {

    @Autowired
    private UserEventRepository userEventRepository;

    public int[] getUserEventsById(int id) {
        return userEventRepository.findEventIdsByUserId(id);
    }

    public List<UserEvent> getAllRelated() {
        return userEventRepository.findAll();
    }
}

