package planIT.Entity.Teams;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import planIT.Entity.Chats.Chat;
import planIT.Entity.Users.User;

/**
 * Represents a Team entity in the system.
 * Each Team has a unique identifier (id), a name, a description, and a set of users associated with it.
 * Users are mapped to teams through a many-to-many relationship.
 *
 * @author Melani Hodge
 *
 */
@Entity
@Tag(name = "Team", description = "Represents a Team entity in the system.")
public class Team {

    // Generated ID for each Team
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of team")
    private int id;

    // Name for each Team
    @Schema(description = "Name of team")
    private String name;

    // Description for each Team
    @Schema(description = "Description of team")
    private String description;

    // @JsonIgnoreProperties - Used to ignore the "users" property when serializing to JSON.
    @JsonIgnoreProperties({"users", "teams", "assignments", "managed", "administrates", "chats", "toDos", "events", "notifications", "tags"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @JsonIgnoreProperties({"managed", "events", "chats", "teams", "notifications", "assignments", "tags", "toDos"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User admin = new User();


    @JsonIgnore //{"team", "chat", "messages"}
    @OneToOne
    @JoinColumn(name = "chat_id")  //chat_id
    private Chat chat;

    /**
     * Constructs a new Team with the specified name and description.
     *
     * @param name        The name of the Team.
     * @param description The description of the Team.
     */
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Default constructor for Team.
     */
    public Team() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    /**
     * Gets the unique identifier of the Team.
     *
     * @return The id of the Team.
     */
    public int getId() { return id; }

    /**
     * Sets the unique identifier of the Team.
     *
     * @param id The new id to set.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the name of the Team.
     *
     * @return The name of the Team.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the Team.
     *
     * @param name The new name to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the description of the Team.
     *
     * @return The description of the Team.
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the Team.
     *
     * @param description The new description to set.
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets team admin
     * @return admin
     */
    public User getAdmin() { return admin; }

    /**
     * Sets team admin
     * @param admin user to be new team admin
     */
    public void setAdmin(User admin) { this.admin = admin; }

    /**
     * Gets the set of users associated with the Team.
     *
     * @return The set of users associated with the Team.
     */
    public Set<User> getUsers() {
        return users;
    }


    /**
     * Sets the associated chat
     * @param chat
     */
    public void setChat(Chat chat){
        this.chat = chat;
    }

    /**
     * Gets the associated chat
     * @return chat
     */
    public Chat getChat(){
        return chat;
    }

}
