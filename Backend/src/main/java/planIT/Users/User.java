package planIT.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    // User constructor (with parameters)
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // User constructor (with parameters)
    public User() { }

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

}
