// Author: Tristan Nono

package com.example.myapplication;

/*
Class for handling events and it's characteristics.
 */
public class Event {
    /*
    The title.
     */
    private String title;

    /*
    The description.
     */
    private String description;

    /*
    Constructor for the event so how it constructs.
     */
    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /*
    Gets the title of the event.
     */
    public String getTitle() {
        return title;
    }

    /*
    Gets the description of the event.
     */
    public String getDescription() {
        return description;
    }
}
