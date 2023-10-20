package planIT.Events;

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
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import planIT.Users.User;

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

    // Date for each Event
    private String date;

    // Start time for each Event
    private String startTime;

    // End time for each Event
    private String endTime;

    private int manager;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    // Event constructor (with parameters)
    public Event(String name, String description, String location, String type, String date, String startTime, String endTime, int manager) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getManager() { return manager; }

    public void setManager(int manager) { this.manager = manager; }

    public Set<User> getUsers() {
        return users;
    }

}
