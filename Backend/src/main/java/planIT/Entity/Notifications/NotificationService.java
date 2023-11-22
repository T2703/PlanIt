package planIT.Entity.Notifications;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.*;

/**
 * Service class for the notification entity
 * @author Melani Hodge
 *
 */
@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Gets all notifications from the repository and returns them as a List object
     * @return notification List
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Gets a notification from the repository by its id number
     * @param id id number of target notification
     * @return notification
     */
    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id);
    }

    /**
     * Saves a new notification and attaches it to a user
     * @param username username for target user
     * @param notification newly created notification
     * @return success
     */
    public String createNotification(String username, Notification notification) {
        notification.setUser(userRepository.findByUsername(username));
        notificationRepository.save(notification);
        return success;
    }

    /**
     * Updates a notification in the database
     * @param id id number of target notification
     * @param request notification object with the updated details
     * @return notification
     */
    public Notification updateNotification(int id, Notification request) {
        Notification notification = notificationRepository.findById(id);
        if (notification == null)
            return null;

        notification.setTitle(request.getTitle());
        notification.setDescription(request.getDescription());

        notificationRepository.save(notification);
        return notificationRepository.findById(id);
    }

    /**
     * Adds a preexisting user to a preexisting notification
     * @param userId id number of target user
     * @param notificationId id number of target notification
     * @return success
     */
    public String addUserToNotification(int userId, int notificationId) {
        User user = userRepository.findById(userId);
        Notification notification = notificationRepository.findById(notificationId);

        user.getNotifications().add(notification);
        notification.setUser(user);

        notificationRepository.save(notification);

        return success;
    }

    /**
     * Deletes notification from repository
     * @param id id number for target notification
     * @return success
     */
    public String deleteNotification(int id) {
        notificationRepository.deleteById(id);
        return success;
    }

    /**
     * Gets all notifications of a certain user and returns them as a List
     * @param username username of target user
     * @return notification List
     */
    public List<Notification> getNotificationByUser(String username) {
        return userRepository.findByUsername(username).getNotifications();
    }
}
