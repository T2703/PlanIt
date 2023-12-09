package notifications;

/**
 * The {@code Notification} class represents a notification with a title, description, and
 * read status. Notifications can also be selected for further actions.
 * <p>
 * Notifications are typically created with a title, description, and initial read status. The
 * class provides methods to retrieve the title, description, and read status of a notification.
 * Additionally, notifications can be marked as selected using the {@code setIsSelected} method.
 * </p>
 *
 * @author Joshua Gutierrez
 */
public class Notification {
    private String title;
    private String description;

    /**
     * Constructs a new {@code Notification} with the specified title, description, and read status.
     *
     * @param title       The title of the notification.
     * @param description The description of the notification.
     */
    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the title of the notification.
     *
     * @return The title of the notification.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the notification.
     *
     * @return The description of the notification.
     */
    public String getDescription() {
        return description;
    }
}
