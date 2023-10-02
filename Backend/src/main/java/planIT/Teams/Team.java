package planIT.Teams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

}
