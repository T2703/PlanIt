// Author: Tristan Nono

package com.example.myapplication;

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
    private String start_time;

    /*
    The end time.
    */
    private String end_time;

    /*
    The date.
    */
    private String date;

    /*
    Constructor for the event so how it constructs.
     */
    public Event(String id, String name, String description, String date, String start_time, String end_time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
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
        return this.start_time;
    }

    public String getEndTime() {
        return this.end_time;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event: " + name + ", Description: " + description;
    }
}
