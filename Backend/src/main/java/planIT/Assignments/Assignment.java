package planIT.Assignments;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private String course;

    private Date dueDate;

    private int userID;

    public Assignment(String title, String description, String course, Date dueDate) {
        this.title = title;
        this.description = description;
        this.course = course;
        this.dueDate = dueDate;
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

}