package planIT.Entity.ToDos;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import planIT.Entity.Users.User;

/**
 * Represents a To-Do entity in the system.
 * Each To-Do has a unique identifier (id), a name, a description, a remind time, a due date,
 * and is associated with a specific user.
 *
 * @author Chris Smith
 *
 */
@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private Date remindTime;

    private Date dueDate;

    // @JsonIgnoreProperties - Used to ignore the "ToDos" property when serializing to JSON.
    @JsonIgnoreProperties("ToDos")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    /**
     * Constructs a new To-Do with the specified name, description, remind time, and due date.
     *
     * @param name        The name of the To-Do.
     * @param description The description of the To-Do.
     * @param remindTime  The remind time for the To-Do.
     * @param dueDate     The due date for the To-Do.
     */
    public ToDo(String name, String description, Date remindTime, Date dueDate) {
        this.name = name;
        this.description = description;
        this.remindTime = remindTime;
        this.dueDate = dueDate;
    }

    /**
     * Default constructor for To-Do.
     */
    public ToDo() {
    }

    /**
     * Gets the unique identifier of the To-Do.
     *
     * @return The id of the To-Do.
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier of the To-Do.
     *
     * @param id The new id to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the name of the To-Do.
     *
     * @return The name of the To-Do.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the To-Do.
     *
     * @param name The new name to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the remind time for the To-Do.
     *
     * @return The remind time for the To-Do.
     */
    public Date getRemindTime() { return remindTime; }

    /**
     * Sets the remind time for the To-Do.
     *
     * @param remindTime The new remind time to set.
     */
    public void setRemindTime(Date remindTime) { this.remindTime = remindTime; }

    /**
     * Gets the due date for the To-Do.
     *
     * @return The due date for the To-Do.
     */
    public Date getDueDate() { return dueDate; }

    /**
     * Sets the due date for the To-Do.
     *
     * @param dueDate The new due date to set.
     */
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    /**
     * Gets the description of the To-Do.
     *
     * @return The description of the To-Do.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the description of the To-Do.
     *
     * @param description The new description to set.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Gets the user associated with the To-Do.
     *
     * @return The user associated with the To-Do.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the To-Do.
     *
     * @param user The new user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
