package planIT.Entity.Notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import planIT.Entity.Users.User;

/**
 * Notifcations entity
 * @author Melani Hodge
 *
 */
@Entity
@Tag(name = "Notification", description = "Notifcations entity.")
public class Notification {

    // Generated ID for each Notification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of notification")
    private int id;

    // Title for each Notification
    @Schema(description = "Title of notification")
    private String title;

    // Description for each Notification
    @Schema(description = "Description of notification")
    private String description;

    // SentTo for each Notification
    @Schema(description = "SentTo of notification")
    private List<String> sentTo;

    // type of Notification
    @Schema(description = "Type of notification")
    private String type;

    @JsonIgnoreProperties("notifications")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Notification constructor with parameters
     * @param title notification title
     * @param description notification description
     */
    public Notification(String title, String description, List<String> sentTo, String type) {
        this.title = title;
        this.description = description;
        this.sentTo = sentTo;
        this.type = type;
    }

    // Notification constructor (without parameters)

    /**
     * Notification constructor without parameters
     */
    public Notification() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    /**
     * Gets the notification id number
     * @return id
     */
    public int getId() { return id; }

    /**
     * Sets notification id number
     * @param id id number
     */
    public void setId(int id){ this.id = id; }

    /**
     * Gets the notification title
     * @return title
     */
    public String getTitle(){ return title; }

    /**
     * Sets notification title
     * @param title notification title
     */
    public void setTitle(String title){ this.title = title; }

    /**
     * Gets the notification description
     * @return description
     */
    public String getDescription() { return description; }

    /**
     * Set notification description
     * @param description description of notification
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets the user attached to notification
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of the notification
     * @param user new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSentTo() { return sentTo; }
    public void setSentTo(List<String> sentTo) { this.sentTo = sentTo; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

}
