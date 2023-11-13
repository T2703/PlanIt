package planIT.Entity.Notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import planIT.Entity.Users.User;

/**
 * Notifcations entity
 * @author Melani Hodge
 *
 */
@Entity
public class Notification {

    // Generated ID for each Notification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Title for each Notification
    private String title;

    // Description for each Notification
    private String description;

    @JsonIgnoreProperties("notifications")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Notification constructor with parameters
     * @param title notification title
     * @param description notification description
     */
    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
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

    public void setDescription(String description) { this.description = description; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
