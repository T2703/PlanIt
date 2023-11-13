package planIT.Entity.Chats;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import planIT.Entity.Messages.Message;
import planIT.Entity.Users.User;

@Entity
public class Chat {

    /**
     * Chat entity's id number
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the chat
     */
    private String name;

    /**
     * Table mapping chats with users
     */
    @JsonIgnoreProperties("chats")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_chat", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    /**
     * Table mapping messages to the chat
     */
    @JsonIgnoreProperties("chat")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_messages", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> messages = new HashSet<>();

    
    /**
     * Chat constructor from String
     * @param name
     */
    public Chat(String name){
        this.name = name;
    }

    /**
     * No param chat constructor
     */
    public Chat(){
    }

    /**
     *
     * @return id number of chat entity
     */
    public int getId() { return id; }

    /**
     * Sets chat's id number
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Returns the name of the chat
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the chat
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the chat's users as a set of Users
     * @return users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Returns the chat's messages as a set of Messages
     * @return messages
     */
    public Set<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the number of users in the chat as an int
     * @return size
     */
    public int chatSize(){
        return users.size();
    }

}
