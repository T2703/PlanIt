package planIT.Events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    @Query("SELECT ue.eventId FROM UserEvent ue WHERE ue.userId = :userId")
    int[] findEventIdsByUserId(int userId);

    UserEvent findById(int id);

    void deleteByUserIdAndEventId(int userId, int eventId);

}

