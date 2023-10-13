package planIT.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import planIT.Events.Event;

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

    /*
     * @OneToMany creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToMany
    private List<Event> events;

    // User constructor (with parameters)
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        events = new ArrayList<>();
    }

    // User constructor (with parameters)
    public User() { events = new ArrayList<>(); }

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvents(Event event){
        this.events.add(event);
    }

}
