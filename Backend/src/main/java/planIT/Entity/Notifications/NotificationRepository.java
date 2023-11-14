package planIT.Entity.Notifications;

import org.springframework.data.jpa.repository.JpaRepository;

// Initializes the Notification repository.
/**
 * Repository for the notification entity
 * @author Melani Hodge
 *
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Finds a notification by id number
     * @param id id number of target notification
     * @return notification
     */
    Notification findById(int id);

    /**
     * Deletes a notification from the repository
     * @param id id number of target notification
     */
    void deleteById(int id);

}
