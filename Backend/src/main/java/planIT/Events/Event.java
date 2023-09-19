package planIT.Events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import planIT.Users.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private Date startTime;

    private Date endTime;

//    private String repeat;
//
//    private boolean isRequired;

    @ManyToMany
    @JsonIgnore
    private List<User> users;

    public Event(String name, String description, Date startTime, Date endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.repeat = repeat;
//        this.isRequired = isRequired;
        users = new ArrayList<User>();
    }

    public Event() {
        users = new ArrayList<User>();
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

    public Date getStartTime() { return startTime; }

    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }

//    public String getRepeat() { return repeat; }
//
//    public void setRepeat(String repeat) { this.repeat = repeat; }
//
//    public boolean getIsRequired() { return isRequired; }
//
//    public void setIsRequired(boolean isRequired) { this.isRequired = isRequired; }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user){
        this.users.add(user);
    }

}
