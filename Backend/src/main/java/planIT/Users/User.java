package planIT.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import planIT.Chats.Chat;
import planIT.Events.Event;
import planIT.Notifications.Notification;

/**
 *
 * @author Melani Hodge
 *
 */

@Entity
public class User {

    // Generated ID for each User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Username for each User
    private String username;

    // Password for each User
    private String password;

    // Email for each User
    private String email;

    @ManyToMany(mappedBy = "users")
    private Set<Event> events = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    @OneToMany
    private List<Notification> notifications;

    // User constructor (with parameters)
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        notifications = new ArrayList<>();
    }

    // User constructor (with parameters)
    public User() { notifications = new ArrayList<>(); }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Set<Event> getEvents() {
        return events;
    }

    // Methods for User-Notifications
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

}
