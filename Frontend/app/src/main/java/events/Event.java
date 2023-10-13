// Author: Tristan Nono

package events;

import androidx.annotation.NonNull;

/*
Class for handling events and it's characteristics.
 */
public class Event {
    private String id;
    /*
    The title.
     */
    private String name;

    /*
    The description.
     */
    private String description;

    /*
    Constructor for the event so how it constructs.
     */
    public Event(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /*
    Gets the title of the event.
     */
    public String getName() {
        return name;
    }

    /*
    Gets the description of the event.
     */
    public String getDescription() {
        return description;
    }

    public String getId() {
        return this.id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event: " + name + ", Description: " + description;
    }
}
