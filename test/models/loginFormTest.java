package models;

import forms.loginForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class loginFormTest {

    loginForm temp;

    @Before
    public void setUp() throws Exception {
        temp = new loginForm();
    }

    @Test
    public void getUsername() {
        temp.setUsername("1");
        assertEquals("1", temp.getUsername());
    }

    @Test
    public void setUsername() {
        temp.setUsername("1");
        assertEquals("1", temp.getUsername());
    }

    @Test
    public void getPassword() {
        temp.setPassword("1");
        assertEquals("1", temp.getPassword());
    }

    @Test
    public void setPassword() {
        temp.setPassword("1");
        assertEquals("1", temp.getPassword());
    }
}