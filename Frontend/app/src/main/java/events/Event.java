package events;

import androidx.annotation.NonNull;

/**
 * @author Joshua Gutierrez
 * The {@code Event} class represents an event with various attributes such as name, description, type,
 * start date, and end date.
 * <p>
 * Instances of this class are used to store information about events in the application.
 * </p>
 *
 * @author Tristan Nono
 */
public class Event {
    /**
     * The unique identifier for the event.
     */
    private String id;

    /**
     * The name of the event.
     */
    private String name;

    /**
     * The description of the event.
     */
    private String description;

    /**
     * The type or category of the event.
     */
    private String type;

    /**
     * The start date of the event.
     */
    private String start_date;

    /**
     * The end date of the event.
     */
    private String end_date;

    /**
     * The location of the event.
     */
    private String location;

    /**
     * Constructs an {@code Event} with the specified parameters.
     *
     * @param id          The unique identifier for the event.
     * @param name        The name of the event.
     * @param description The description of the event.
     * @param type        The type or category of the event.
     * @param start_date  The start date of the event.
     * @param end_date    The end date of the event.
     */
    public Event(String id, String name, String description, String type, String start_date, String end_date, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.location = location;
    }

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the unique identifier for the event.
     *
     * @return The unique identifier for the event.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the start date of the event.
     *
     * @return The start date of the event.
     */
    public String getStartTime() {
        return this.start_date;
    }

    /**
     * Gets the end date of the event.
     *
     * @return The end date of the event.
     */
    public String getEndTime() {
        return this.end_date;
    }

    /**
     * Gets the type or category of the event.
     *
     * @return The type or category of the event.
     */
    public String getType() {
        return this.type;
    }

    public String getLocation() {return this.location; }

    /**
     * Returns a string representation of the event.
     *
     * @return A string representation of the event.
     */
    @NonNull
    @Override
    public String toString() {
        return "Event: " + name + ", Description: " + description;
    }
}
