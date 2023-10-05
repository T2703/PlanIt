// Author: Tristan Nono

package com.example.myapplication;

import androidx.annotation.NonNull;

/*
Class for handling events and it's characteristics.
 */
public class Event {
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
    public Event(String name, String description) {
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

    @NonNull
    @Override
    public String toString() {
        return "Event: " + name + ", Description: " + description;
    }
}
