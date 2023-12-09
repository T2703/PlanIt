package planIT.Entity.Assignments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import planIT.Entity.Users.User;

/**
 * Assignment entity.  Contains info for a user assignment, its due date, and whether or not the assignment is complete.
 */
@Entity
@Tag(name = "Assignment", description = "Assignment entity.  Contains info for a user assignment, its due date, and whether or not the assignment is complete.")
@RestController
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of assignment")
    private int id;

    @Schema(description = "Title of assignment")
    private String title;

    @Schema(description = "Description of assignment")
    private String description;

    @Schema(description = "Courses for the assignment")
    private String course;

    @Schema(description = "Due date of the assignment")
    private Date dueDate;

    @Schema(description = "Checks if the assignment is completed.")
    private boolean isCompleted;

    /**
     * Tracks the user entity that this assignment belongs to.
     */
    @JsonIgnoreProperties("assignments")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user = new User();


    /**
     * Constructor for assignment entity.
     * @param title Title of the assignment.
     * @param description Description of the assignment.
     * @param course Course of the assignment.
     * @param dueDate The date the assignment is due.
     * @param isCompleted Whether or not the assignment is completed.
     */
    public Assignment(String title, String description, String course, Date dueDate, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    /**
     * Empty constructor for assignment
     */
    public Assignment() {
    }

    /**
     * Gets the id number of the assignment
     * @return assignment id
     */
    public int getId() { return id; }

    /**
     * Sets assignment id number
     * @param id new id number
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the title of the assingment
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Gets the assignment course
     * @return course
     */
    public String getCourse(){
        return course;
    }

    /**
     * Gets the assignment description
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Gets the assignment dueDate
     * @return dueDate
     */
    public Date getDueDate() { return dueDate; }

    /**
     * Gets the completion status of the assignment
     * @return isCompleted
     */
    public boolean getIsCompleted() { return isCompleted; }

    /**
     * Sets the assignment title
     * @param title new title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the assignment course
     * @param course new course
     */
    public void setCourse(String course){
        this.course = course;
    }

    /**
     * Sets the assignment description
     * @param description new description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets the dueDate of the assignment
     * @param dueDate new dueDate
     */
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    /**
     * Sets the completion status of the assignment
     * @param isCompleted
     */
    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    /**
     * Gets the assignment's user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the assignment's user
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

}
