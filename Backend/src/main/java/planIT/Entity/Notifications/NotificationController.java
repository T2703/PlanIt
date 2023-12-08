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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

/**
 * Controller class for the notification entity
 * @author Melani Hodge
 *
 */
@RestController
@Tag(name = "Notification Management System", description = "Operations pertaining to notification management")
public class NotificationController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    // GET method - retreives a notification from the database.
    /**
     * Gets a notification from the repository from an id number
     * @param id id number for target notification
     * @return notification
     */
    @GetMapping(path = "/notifications/{id}")
    @Operation(summary = "Get a Notification by Id", description = "Gets a notification from the repository from an id number")
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
    @Operation(summary = "Create a new Notification for a user", description = "Creates a notification and attaches it to a user")
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
    @Operation(summary = "Get Notifications for a specific user", description = "Gets all of a user's notifications and returns them as a List")
    public List<Notification> getUserNotifications(@PathVariable String username) {
        return notificationService.getNotificationByUser(username);
    }

    @GetMapping(path = "/users/{username}/notifications-unread")
    @Operation(summary = "Get Notifications for a specific user", description = "Gets all of a user's notifications and returns them as a List")
    public int getUserUnreadNotifications(@PathVariable String username) {
        return notificationService.getUnreadNotificationByUser(username);
    }

    // DELETE method - deletes a notification from the database.
    /**
     * Deletes a notification from the repository
     * @param id id number of the target notification
     * @return success
     */
    @DeleteMapping(path = "users/{username}/notifications/{id}")
    @Operation(summary = "Delete a Notification by Id", description = "Deletes a notification from the repository")
    public String deleteNotification(@PathVariable String username, @PathVariable int id) {
        User user = userService.findUserByUsername(username);
        user.getNotifications().remove(notificationService.getNotificationById(id));
        return notificationService.deleteNotification(id);
    }
}

