package models;

import forms.newEventForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class newEventFormTest {

    newEventForm temp;

    @Before
    public void setUp() throws Exception {
        temp = new newEventForm("evName", "evDes", "evCU", "evCou", "evSta", "evCit", "evZip", "evAd");
    }

    @Test
    public void getEventName() {
        assertTrue(temp.getEventName().equals("evName"));
    }


    @Test
    public void getEventDescription() {

        assertTrue(temp.getEventDescription().equals("evDes"));
    }


    @Test
    public void getEventCreaterUsername() {

        assertTrue(temp.getEventCreaterUsername().equals("evCU"));
    }


    @Test
    public void getEventCountry() {

        assertTrue(temp.getEventCountry().equals("evCou"));
    }


    @Test
    public void getEventState() {

        assertTrue(temp.getEventState().equals("evSta"));
    }


    @Test
    public void getEventCity() {

        assertTrue(temp.getEventState().equals("evSta"));
    }

    @Test
    public void getEventZip() {

        assertTrue(temp.getEventZip().equals("evZip"));
    }


    @Test
    public void getEventAddress() {

        assertTrue(temp.getEventAddress().equals("evAd"));
    }

}