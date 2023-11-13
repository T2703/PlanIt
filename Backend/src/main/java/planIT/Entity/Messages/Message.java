package planIT.Entity.Messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import planIT.Entity.Chats.Chat;
import planIT.Entity.Notifications.Notification;
import planIT.Entity.Users.User;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * Message entity for containing message info within a chat
 * @author Melani Hodge
 *
 */

@Entity
public class Message {

    // Generated ID for each Message
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnoreProperties("message")
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;


    // Body for each Message
    private String body;

    // Send time for each Message
    private Date sendTime;

    // Receive time for each Message
    private Date receiveTime;

    // Message constructor (with parameters)

    /**
     * Constructor for message
     * @param body text content of a message
     * @param sendTime time message was sent
     * @param receiveTime time message was received
     */
    public Message(String body, Date sendTime, Date receiveTime) {
        this.body = body;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
    }

    // Message constructor (without parameters)

    /**
     * Empty message constructor
     */
    public Message() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    /**
     * Returns message id numbers
     * @return id
     */
    public int getId() { return id; }

    /**
     * Sets the message id number
     * @param id new id number for message
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Returns the message body
     * @return body
     */
    public String getBody(){
        return body;
    }

    /**
     * Sets the body of the message
     * @param body new message body
     */
    public void setBody(String body){
        this.body = body;
    }

    /**
     * Returns time the message was sent
     * @return sendTime
     */
    public Date getSendTime() { return sendTime; }

    /**
     * Sets the time message was sent
     * @param sendTime time message was sent
     */
    public void setSendTime(Date sendTime) { this.sendTime = sendTime; }

    /**
     * Gets the time message was received
     * @return receiveTime
     */
    public Date getReceiveTime() { return receiveTime; }

    /**
     * Sets the time message was received
     * @param receiveTime time message was received
     */
    public void setReceiveTime(Date receiveTime) { this.receiveTime = receiveTime; }

}
