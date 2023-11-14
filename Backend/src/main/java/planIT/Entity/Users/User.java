package planIT.Entity.Users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;

import planIT.Entity.Assignments.Assignment;
import planIT.Entity.Chats.Chat;
import planIT.Entity.Events.Event;
import planIT.Entity.Notifications.Notification;
import planIT.Entity.Tags.Tag;
import planIT.Entity.Teams.Team;
import planIT.Entity.ToDos.ToDo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user in the PlanIT application.
 * Each user has a unique identifier, username, password, and email.
 * Additionally, a user can be associated with various events, chats, teams, notifications,
 * assignments, tags, to-dos, and events they manage.
 *
 * This class is annotated as an Entity to be managed by JPA (Java Persistence API) and
 * includes relationships with other entities like Event, Chat, Team, Notification,
 * Assignment, Tag, and ToDos.
 *
 * @author Melani Hodge
 *
 */
@Entity
public class User {

    // Generated ID for each User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of user")
    private int id;

    // Username for each User
    @Schema(description = "Username of user")
    private String username;

    // Password for each User
    @Schema(description = "Password of user")
    private String password;

    // Email for each User
    @Schema(description = "Email of user")
    private String email;

    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Event> events = new HashSet<>();

    @JsonIgnoreProperties
    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    @JsonIgnoreProperties
    @ManyToMany(mappedBy = "users")
    private Set<Team> teams = new HashSet<>();

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Assignment> assignments;

    @JsonIgnoreProperties("manager")
    @OneToMany(mappedBy = "manager")
    private List<Event> managed;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Tag> tags;

    @JsonIgnoreProperties("user")
    @OneToMany
    private List<ToDo> toDos;

    /**
     * Constructs a new User with the specified username, password, and email.
     * Initializes the notifications with an empty ArrayList.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email    The email address of the user.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        notifications = new ArrayList<>();
    }

    /**
     * Default constructor for User.
     * Initializes the notifications with an empty ArrayList.
     */
    public User() { notifications = new ArrayList<>(); }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's id.
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The new id to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() { return password; }

    /**
     * Sets the password of the user.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() { return email; }

    /**
     * Sets the email address of the user.
     *
     * @param email The new email address to set.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the set of events associated with the user.
     *
     * @return The set of events associated with the user.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Gets the set of chats associated with the user.
     *
     * @return The set of chats associated with the user.
     */
    public Set<Chat> getChats() {
        return chats;
    }

    /**
     * Gets the list of notifications associated with the user.
     *
     * @return The list of notifications.
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Sets the list of notifications associated with the user.
     *
     * @param notifications The new list of notifications to set.
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Adds a notification to the user's list of notifications.
     *
     * @param notification The notification to add.
     */
    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    /**
     * Gets the list of assignments associated with the user.
     *
     * @return The list of assignments.
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Sets the list of assignments associated with the user.
     *
     * @param assignments The new list of assignments to set.
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Adds an assignment to the user's list of assignments.
     *
     * @param assignment The assignment to add.
     */
    public void addAssignment(Assignment assignment){
        this.assignments.add(assignment);
    }

    /**
     * Gets the list of tags associated with the user.
     *
     * @return The list of tags.
     */
    public List<Tag> getTags() {
        return tags;
    }


    /**
     * Sets the list of tags associated with the user.
     *
     * @param tags The new list of tags to set.
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Adds a tag to the user's list of tags.
     *
     * @param tag The tag to add.
     */
    public void addTags(Tag tag){
        this.tags.add(tag);
    }

    /**
     * Gets the list of To-Do items associated with the user.
     *
     * @return The list of To-Do items.
     */
    public List<ToDo> getToDos() {
        return toDos;
    }

    /**
     * Sets the list of To-Do items associated with the user.
     *
     * @param toDos The new list of To-Do items to set.
     */
    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    /**
     * Adds a To-Do item to the user's list of To-Do items.
     *
     * @param toDo The To-Do item to add.
     */
    public void addToDos(ToDo toDo){
        this.toDos.add(toDo);
    }

    /**
     * Gets the set of teams associated with the user.
     *
     * @return The set of teams.
     */
    public Set<Team> getTeams(){
        return teams;
    }

    /**
     * Gets the list of events that the user manages.
     *
     * @return The list of managed events.
     */
    public List<Event> getManaged() {
        return managed;
    }

    /**
     * Adds an event to the list of events that the user manages.
     *
     * @param event The event to add.
     */
    public void addManaged(Event event){
        this.managed.add(event);
    }

}
