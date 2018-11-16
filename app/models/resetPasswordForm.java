package models;

/**
 * @author: Lucas Buccilli
 * Date: 10/22/18
 * Time: 2:36 PM
 * Project Name: meetup-app
 * File Name: resetPasswordForm
 */
public class resetPasswordForm {
    private String email;
    private String resetCode;
    private String tempNewPassword;
    private String tempNewPasswordConfirm;

    public String getTempNewPasswordConfirm() {
        return tempNewPasswordConfirm;
    }

    public void setTempNewPasswordConfirm(String tempNewPasswordConfirm) {
        this.tempNewPasswordConfirm = tempNewPasswordConfirm;
    }

    public String getTempNewPassword() {
        return tempNewPassword;
    }

    public void setTempNewPassword(String tempNewPassword) {
        this.tempNewPassword = tempNewPassword;
    }

    public resetPasswordForm(String email) {
        this.email = email;
    }

    public resetPasswordForm() {
        this.email = null;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
