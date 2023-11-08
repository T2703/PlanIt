package planIT.Entity.Events;

import javax.persistence.*;


@Entity
@Table(name = "event_user")
public class UserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "event_id")
    private int eventId;

    // Constructors, getters, and setters

    // Constructors
    public UserEvent(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public UserEvent() {
    }

    public int getId() { return id;}

    public int getUserId() { return userId;}

    public int getEventId() { return eventId;}

    public void setId(int id) { this.id = id;}

    public void setUserId(int userId) { this.userId = userId;}

    public void setEventId(int eventId) { this.eventId = eventId;}
}

