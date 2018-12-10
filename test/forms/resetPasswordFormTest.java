package forms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class resetPasswordFormTest {

    resetPasswordForm temp;

    @Before
    public void setUp() throws Exception {
        temp = new resetPasswordForm();
        temp = new resetPasswordForm("1");
    }

    @Test
    public void getTempNewPasswordConfirm() {
        temp.setTempNewPasswordConfirm("1");
        assertEquals("1", temp.getTempNewPasswordConfirm());
    }


    @Test
    public void getTempNewPassword() {
        temp.setTempNewPassword("1");
        assertEquals("1", temp.getTempNewPassword());
    }


    @Test
    public void getResetCode() {
        temp.setResetCode("1");
        assertEquals("1", temp.getResetCode());
    }


    @Test
    public void getEmail() {
        temp.setEmail("1");
        assertEquals("1", temp.getEmail());
    }


}