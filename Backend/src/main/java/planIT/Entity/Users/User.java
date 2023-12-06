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

    @Schema(description = "List of User's Followers")
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonIgnoreProperties({"followers", "following", "events", "chats", "teams", "notifications", "assignments", "managed", "administrates", "tags", "toDos"})
    private List<User> followers = new ArrayList<>();

    @Schema(description = "List of User's Following")
    @ManyToMany(mappedBy = "followers")
    @JsonIgnoreProperties({"followers", "following", "events", "chats", "teams", "notifications", "assignments", "managed", "administrates", "tags", "toDos"})
    private List<User> following = new ArrayList<>();

    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Event> events = new HashSet<>();

    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    @JsonIgnoreProperties("users")
    @ManyToMany(mappedBy = "users")
    private List<Team> teams;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    private List<Assignment> assignments;

    @JsonIgnoreProperties({"manager", "users"})
    @OneToMany(mappedBy = "manager")
    private List<Event> managed;

    @JsonIgnoreProperties({"admin", "users"})
    @OneToMany(mappedBy = "admin")
    private List<Team> administrates;

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

    public List<User> getFollowers() { return followers;}

    public List<User> getFollowing() { return following;}


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
     * Gets the list of assignments associated with the user.
     *
     * @return The list of assignments.
     */
    public List<Assignment> getAssignments() {
        return assignments;
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
     * Gets the list of To-Do items associated with the user.
     *
     * @return The list of To-Do items.
     */
    public List<ToDo> getToDos() {
        return toDos;
    }

    /**
     * Gets the set of teams associated with the user.
     *
     * @return The set of teams.
     */
    public List<Team> getTeams(){
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
     * Gets the list of teams that the user administrates.
     *
     * @return The list of admin teamed.
     */
    public List<Team> getAdministrates() {
        return administrates;
    }

}
