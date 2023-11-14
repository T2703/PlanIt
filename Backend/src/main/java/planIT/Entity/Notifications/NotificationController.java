package planIT.Entity.Notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for the notification entity
 * @author Melani Hodge
 *
 */
@RestController
public class NotificationController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private NotificationService notificationService;

    // GET method - retreives all notifications from the database.

    /**
     * Returns all notifications from the repository as a List object
     * @return notifications
     */
    @GetMapping(path = "/notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // GET method - retreives a notification from the database.
    /**
     * Gets a notification from the repository from an id number
     * @param id id number for target notification
     * @return notification
     */
    @GetMapping(path = "/notifications/{id}")
    public Notification getNotificationById(@PathVariable int id) {
        return notificationService.getNotificationById(id);
    }

    // POST method - adds a notification to the database.
    /**
     * Creates a notification and attaches it to a user
     * @param username username of the target user
     * @param notification newly created notification
     * @return success
     */
    @PostMapping(path = "/users/{username}/notifications")
    public String createNotification(@PathVariable String username, @RequestBody Notification notification) {
        return notificationService.createNotification(username, notification);
    }

    // GET method - gets notifications from a specific user.
    /**
     * Gets all of a user's notifications and returns them as a List
     * @param username username of the target user
     * @return notifications List
     */
    @GetMapping(path = "/users/{username}/notifications")
    public List<Notification> getUserNotifications(@PathVariable String username) {
        return notificationService.getNotificationByUser(username);
    }

    // PUT method - updates a notification in the database.
    /**
     * Updates a notification in the repository
     * @param id id number for the target notification
     * @param notification newly created notification
     * @return notification
     */
    @PutMapping(path = "/notifications/{id}")
    public Notification updateNotification(@PathVariable int id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    // DELETE method - deletes a notification from the database.
    /**
     * Deletes a notification from the repository
     * @param id id number of the target notification
     * @return success
     */
    @DeleteMapping(path = "/notifications/{id}")
    public String deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id);
    }
}

