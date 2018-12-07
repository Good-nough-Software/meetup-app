package forms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserAccountSettingsDataTest {

    UserAccountSettingsData temp;

    @Before
    public void setUp() throws Exception {
        temp = new UserAccountSettingsData();
    }


    @Test
    public void getUsername() {
        temp.setUsername("1");
        assertEquals("1", temp.getUsername());
    }


    @Test
    public void getPassword() {
        temp.setPassword("1");
        assertEquals("1", temp.getPassword());
    }


    @Test
    public void getPhone() {
        temp.setPhone("1");
        assertEquals("1", temp.getPhone());
    }


    @Test
    public void getEmail() {
        temp.setEmail("1");
        assertEquals("1", temp.getEmail());
    }


    @Test
    public void getLocations() {
        temp.setLocations("1");
        assertEquals("1", temp.getLocations());
    }


    @Test
    public void getHome() {
        temp.setHome("1");
        assertEquals("1", temp.getHome());
    }


}