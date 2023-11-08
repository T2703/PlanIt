package planIT.Notifications;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Assignments.Assignment;
import planIT.Users.*;

/**
 *
 * @author Melani Hodge
 *
 */

// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(int id) {
        return notificationRepository.findById(id);
    }

    public String createNotification(String username, Notification notification) {
        notification.setUser(userRepository.findByUsername(username));
        notificationRepository.save(notification);
        return success;
    }

    public Notification updateNotification(int id, Notification request) {
        Notification notification = notificationRepository.findById(id);
        if (notification == null)
            return null;

        notification.setTitle(request.getTitle());
        notification.setDescription(request.getDescription());

        notificationRepository.save(notification);
        return notificationRepository.findById(id);
    }

    public String addUserToNotification(int userId, int notificationId) {
        User user = userRepository.findById(userId);
        Notification notification = notificationRepository.findById(notificationId);

        user.getNotifications().add(notification);
        notification.setUser(user);

        notificationRepository.save(notification);

        return success;
    }

    public String deleteNotification(int id) {
        notificationRepository.deleteById(id);
        return success;
    }

    public List<Notification> getNotificationByUser(String username) {
        return userRepository.findByUsername(username).getNotifications();
    }
}
