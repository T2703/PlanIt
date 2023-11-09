package planIT.Entity.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;

import planIT.Entity.Users.User;

/**
 *
 * @author Melani Hodge
 *
 */

@Entity
public class Event {

    // Generated ID for each Event
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Name for each Event
    private String name;

    // Description for each Event
    private String description;

    // Location for each Event
    private String location;

    // Type for each Event
    private String type;

    // Start time for each Event
    private Date startDate;

    // End time for each Event
    private Date endDate;

    private int manager;

    @JsonIgnoreProperties("events")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    // Event constructor (with parameters)
    public Event(String name, String description, String location, String type, Date startDate, Date endDate, int manager) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
    }

    // Event constructor (without parameters)
    public Event() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    public int getId() { return id; }

    public void setId(int id){ this.id = id; }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public int getManager() { return manager; }

    public void setManager(int manager) { this.manager = manager; }

    public Set<User> getUsers() {
        return users;
    }

}
