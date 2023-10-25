// Author: Tristan Nono

package events;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    The start time.
    */
    private String start_date;

    /*
    The end time.
    */
    private String end_date;


    /*
    Constructor for the event so how it constructs.
     */
    public Event(String id, String name, String description, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
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

    public String getStartTime() {
        return this.start_date;
    }

    public String getEndTime() {
        return this.end_date;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event: " + name + ", Description: " + description;
    }
}
