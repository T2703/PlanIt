package planIT.Events;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the Event repository.
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(int id);
    void deleteById(int id);

}
