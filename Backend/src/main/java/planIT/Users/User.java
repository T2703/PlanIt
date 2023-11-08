package planIT.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

import planIT.Assignments.Assignment;
import planIT.Chats.Chat;
import planIT.Events.Event;
import planIT.Notifications.Notification;
import planIT.Tags.Tag;
import planIT.ToDos.ToDo;

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

    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    @OneToMany
    private List<Notification> notifications;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Assignment> assignments;

    @OneToMany
    private List<Tag> tags;

    @OneToMany
    private List<ToDo> toDos;

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

    public Set<Chat> getChats() {
        return chats;
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

    // Methods for User-Assignments
    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addAssignment(Assignment assignment){
        this.assignments.add(assignment);
    }

    // Methods for User-Tags
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTags(Tag tag){
        this.tags.add(tag);
    }

    // Methods for User-ToDos
    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    public void addToDos(ToDo toDo){
        this.toDos.add(toDo);
    }

}
