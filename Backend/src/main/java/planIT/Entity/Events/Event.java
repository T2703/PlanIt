package planIT.Entity.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import planIT.Entity.Users.User;

/**
 * Event entity.  Tracks all details of a user's event.
 * @author Melani Hodge
 *
 */

@Entity
@Tag(name = "Event", description = "Event entity.  Tracks all details of a user's event.")
public class Event {

    // Generated ID for each Event
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of event")
    private int id;

    // Name for each Event
    @Schema(description = "Name of event")
    private String name;

    // Description for each Event
    @Schema(description = "Description of event")
    private String description;

    // Location for each Event
    @Schema(description = "Location of event")
    private String location;

    // Type for each Event
    @Schema(description = "Type of event")
    private String type;

    // Start time for each Event
    @Schema(description = "Start date of event")
    private Date startDate;

    // End time for each Event
    @Schema(description = "End date of event")
    private Date endDate;

    @JsonIgnoreProperties({"managed", "events", "chats", "teams", "notifications", "assignments", "tags", "toDos"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User manager;

    @JsonIgnoreProperties({"events", "managed", "chats", "teams", "notifications", "assignments", "tags", "toDos"})
    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    // Event constructor (with parameters)
    /**
     * Event constructor with parameters
     * @param name event name
     * @param description event description
     * @param location event location
     * @param type event type
     * @param startDate event start date
     * @param endDate event end date
     */
    public Event(String name, String description, String location, String type, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Event constructor (without parameters)

    /**
     * Event constructor with no parameters
     */
    public Event() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    /**
     * Gets event id number
     * @return id
     */
    public int getId() { return id; }

    /**
     * Sets event id number
     * @param id id number
     */
    public void setId(int id){ this.id = id; }

    /**
     * Gets event name
     * @return name of the event
     */
    public String getName(){ return name; }

    /**
     * Sets event name
     * @param name new event name
     */
    public void setName(String name){ this.name = name; }

    /**
     * Get event description
     * @return description
     */
    public String getDescription() { return description; }

    /**
     * Sets event description
     * @param description new event description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Get event location
     * @return location
     */
    public String getLocation() { return location; }

    /**
     * Set event location
     * @param location new event location
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * Get event type
     * @return type
     */
    public String getType() { return type; }

    /**
     * Set event type
     * @param type new event type
     */
    public void setType(String type) { this.type = type; }

    /**
     * Get event start date
     * @return startDate
     */
    public Date getStartDate() { return startDate; }

    /**
     * Set event start date
     * @param startDate new event start date
     */
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    /**
     * Get event end date
     * @return endDate
     */
    public Date getEndDate() { return endDate; }

    /**
     * Set end date of event
     * @param endDate new event end date
     */
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    /**
     * Gets event manager
     * @return manager
     */
    public User getManager() { return manager; }

    /**
     * Sets event manager
     * @param manager user to be new event manager
     */
    public void setManager(User manager) { this.manager = manager; }

    /**
     * Gets all users of an event and returns them as a Set
     * @return users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Adds a user the event
     * @param user user to add
     */
    public void addUser(User user) {
        this.users.add(user);
    }

}
