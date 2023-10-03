package planIT.Messages;

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
public class Message {

    // Generated ID for each Message
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Body for each Message
    private String body;

    // Send time for each Message
    private Date sendTime;

    // Receive time for each Message
    private Date receiveTime;

    // Message constructor (with parameters)
    public Message(String body, Date sendTime, Date receiveTime) {
        this.body = body;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
    }

    // Message constructor (without parameters)
    public Message() { }

    /* =============== GETTER & SETTER FUNCTIONS =============== */

    public int getId() { return id; }

    public void setId(int id){
        this.id = id;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    public Date getSendTime() { return sendTime; }

    public void setSendTime(Date sendTime) { this.sendTime = sendTime; }

    public Date getReceiveTime() { return receiveTime; }

    public void setReceiveTime(Date receiveTime) { this.receiveTime = receiveTime; }

}
