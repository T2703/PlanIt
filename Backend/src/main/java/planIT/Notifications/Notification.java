package planIT.Notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import planIT.Users.User;

/**
 *
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


    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Notification constructor (without parameters)
    public Notification() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    public int getId() { return id; }

    public void setId(int id){ this.id = id; }

    public String getTitle(){ return title; }

    public void setTitle(String title){ this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
