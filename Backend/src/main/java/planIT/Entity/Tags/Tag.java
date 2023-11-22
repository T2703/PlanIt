package planIT.Entity.Tags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import io.swagger.v3.oas.annotations.media.Schema;
import planIT.Entity.Users.User;


@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID of tag")
    private int id;

    @Schema(description = "Name of tag")
    private String name;

    @Schema(description = "Description of tag")
    private String description;

    @JsonIgnoreProperties("tags")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Tag() {

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

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
