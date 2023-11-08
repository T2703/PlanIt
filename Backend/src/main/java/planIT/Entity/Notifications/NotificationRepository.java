package planIT.Entity.Notifications;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the Notification repository.
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findById(int id);
    void deleteById(int id);

}
