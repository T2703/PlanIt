package planIT.Entity.ToDos;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import planIT.Entity.Users.User;

@Entity
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private Date remindTime;

    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public ToDo(String name, String description, Date remindTime, Date dueDate) {
        this.name = name;
        this.description = description;
        this.remindTime = remindTime;
        this.dueDate = dueDate;
    }

    public ToDo() {
    }

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getRemindTime() { return remindTime; }

    public void setRemindTime(Date remindTime) { this.remindTime = remindTime; }

    public Date getDueDate() { return dueDate; }

    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
