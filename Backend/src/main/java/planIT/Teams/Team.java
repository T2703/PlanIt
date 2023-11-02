package planIT.Teams;

import planIT.Users.User;

import java.util.*;

import javax.persistence.*;

/**
 *
 * @author Melani Hodge
 *
 */

@Entity
public class Team {

    // Generated ID for each Team
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    // Name for each Team
    private String name;

    // Description for each Team
    private String description;

    // Team constructor (with parameters)
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Team constructor (without parameters)
    public Team() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

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

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Set<User> getUsers() {
        return users;
    }

}
