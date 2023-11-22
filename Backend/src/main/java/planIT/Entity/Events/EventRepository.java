package planIT.Entity.Events;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Events
 * @author Melani Hodge
 *
 */

// Initializes the Event repository.
public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * Returns an event from repository by id
     * @param id id number of target event
     * @return event
     */
    Event findById(int id);

    /**
     * Delete an event from the repository
     * @param id id number of target event
     */
    void deleteById(int id);

}
