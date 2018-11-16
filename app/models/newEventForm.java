package models;

/**
 * @author: Lucas Buccilli
 * Date: 11/16/18
 * Time: 2:52 PM
 * Project Name: meetup-app
 * File Name: newEventForm
 */
public class newEventForm {
    private String eventName;
    private String eventDiscription;
    private String eventCreaterUsername;
    private String eventLocation;

    public newEventForm(String eventName, String eventDiscription, String eventCreaterUsername, String eventLocation) {
        this.eventName = eventName;
        this.eventDiscription = eventDiscription;
        this.eventCreaterUsername = eventCreaterUsername;
        this.eventLocation = eventLocation;
    }

    public newEventForm() {
        this.eventName = "";
        this.eventDiscription = "";
        this.eventCreaterUsername = "";
        this.eventLocation = "";
    }



    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDiscription() {
        return eventDiscription;
    }

    public void setEventDiscription(String eventDiscription) {
        this.eventDiscription = eventDiscription;
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
