package forms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class newEventFormTest {

    newEventForm temp;

    @Before
    public void setUp() throws Exception {
        temp = new newEventForm("evName", "evDes", "evCU", "evCou", "evSta", "evCit", "evZip", "evAd");
    }

    @Test
    public void getEventName() {
        temp.setEventName("1");
        assertEquals("1", temp.getEventName());
    }


    @Test
    public void getEventDescription() {
        temp.setEventDescription("1");
        assertEquals("1", temp.getEventDescription());
    }


    @Test
    public void getEventCreaterUsername() {
        temp.setEventCreaterUsername("1");
        assertEquals("1", temp.getEventCreaterUsername());
    }


    @Test
    public void getEventCountry() {
        temp.setEventCountry("1");
        assertEquals("1", temp.getEventCountry());
    }


    @Test
    public void getEventState() {
        temp.setEventState("1");
        assertEquals("1", temp.getEventState());
    }


    @Test
    public void getEventCity() {
        temp.setEventCity("1");
        assertEquals("1", temp.getEventCity());
    }

    @Test
    public void getEventZip() {
        temp.setEventZip("1");
        assertEquals("1", temp.getEventZip());
    }


    @Test
    public void getEventAddress() {
        temp.setEventAddress("1");
        assertEquals("1", temp.getEventAddress());
    }

}