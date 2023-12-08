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

    public int getUnreadNotificationByUser(String username) {
        User user = userRepository.findByUsername(username);

        List<Notification> notifications = user.getNotifications();

        int count = 0;

        for (int i = 0; i < notifications.size(); i++) {
            if (!notifications.get(i).isSeen()) {
                count++;
            }
        }

        return count;
    }
}
