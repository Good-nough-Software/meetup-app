package forms;

import java.util.Date;

/**
 * @author: Lucas Buccilli
 * Date: 11/16/18
 * Time: 2:52 PM
 * Project Name: meetup-app
 * File Name: newEventForm
 */


public class newEventForm {
    private String eventName;
    private String eventDescription;
    private String eventCreaterUsername;
    private String eventCountry;
    private String eventState;
    private String eventCity;
    private String eventZip;
    private String eventAddress;
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public newEventForm() {}

    public newEventForm(String eventName, String eventDescription, String eventCreaterUsername, String eventCountry, String eventState, String eventCity, String eventZip, String eventAddress) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCreaterUsername = eventCreaterUsername;
        this.eventCountry = eventCountry;
        this.eventState = eventState;
        this.eventCity = eventCity;
        this.eventZip = eventZip;
        this.eventAddress = eventAddress;
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


    public String getEventCountry() {
        return eventCountry;
    }

    public void setEventCountry(String eventCountry) {
        this.eventCountry = eventCountry;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventZip() {
        return eventZip;
    }

    public void setEventZip(String eventZip) {
        this.eventZip = eventZip;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }
}
