package planIT.Entity.Assignments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import planIT.Entity.Users.User;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private String course;

    private Date dueDate;

    private boolean isCompleted;

    @JsonIgnoreProperties("assignments")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Assignment(String title, String description, String course, Date dueDate, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public Assignment() {
    }

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getCourse(){
        return course;
    }

    public String getDescription(){
        return description;
    }

    public Date getDueDate() { return dueDate; }

    public boolean getIsCompleted() { return isCompleted; }

    public void setTitle(String title){
        this.title = title;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public void setIsCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
