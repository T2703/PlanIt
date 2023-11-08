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
 *
 * @author Melani Hodge
 *
 */

@RestController
public class NotificationController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private NotificationService notificationService;

    // GET method - retreives all notifications from the database.
    @GetMapping(path = "/notifications")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // GET method - retreives a notification from the database.
    @GetMapping(path = "/notifications/{id}")
    public Notification getNotificationById(@PathVariable int id) {
        return notificationService.getNotificationById(id);
    }

    // POST method - adds a notification to the database.
    @PostMapping(path = "/users/{username}/notifications")
    public String createNotification(@PathVariable String username, @RequestBody Notification notification) {
        return notificationService.createNotification(username, notification);
    }

    // GET method - gets notifications from a specific user.
    @GetMapping(path = "/users/{username}/notifications")
    public List<Notification> getUserNotifications(@PathVariable String username) {
        return notificationService.getNotificationByUser(username);
    }

    // PUT method - updates a notification in the database.
    @PutMapping(path = "/notifications/{id}")
    public Notification updateNotification(@PathVariable int id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    // DELETE method - deletes a notification from the database.
    @DeleteMapping(path = "/notifications/{id}")
    public String deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id);
    }
}

