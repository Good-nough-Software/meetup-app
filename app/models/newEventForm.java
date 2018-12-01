package models;

import javax.persistence.Entity;

/**
 * @author: Lucas Buccilli
 * Date: 11/16/18
 * Time: 2:52 PM
 * Project Name: meetup-app
 * File Name: newEventForm
 */
@Entity
public class newEventForm {
    private String eventName;
    private String eventDescription;
    private String eventCreaterUsername;
    private String eventLocation;

    public newEventForm(String eventName, String eventDescription, String eventCreaterUsername, String eventLocation) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCreaterUsername = eventCreaterUsername;
        this.eventLocation = eventLocation;
    }

    public newEventForm() {
        this.eventName = "";
        this.eventDescription = "";
        this.eventCreaterUsername = "";
        this.eventLocation = "";
    }



    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCreaterUsername() {
        return eventCreaterUsername;
    }

    public void setEventCreaterUsername(String eventCreaterUsername) {
        this.eventCreaterUsername = eventCreaterUsername;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
