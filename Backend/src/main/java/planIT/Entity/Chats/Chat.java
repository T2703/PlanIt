package planIT.Entity.Chats;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import planIT.Entity.Messages.Message;
import planIT.Entity.Users.User;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @JsonIgnoreProperties("chats")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_chat", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @JsonIgnoreProperties("chat")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_messages", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> messages = new HashSet<>();


    public Chat(String name){
        this.name = name;
    }

    public Chat(){
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

    public Set<User> getUsers() {
        return users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public int chatSize(){
        return users.size();
    }

}
