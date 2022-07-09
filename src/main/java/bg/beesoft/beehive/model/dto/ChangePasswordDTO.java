package bg.beesoft.beehive.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ChangePasswordDTO {
    @NotEmpty
    @Size(min=5, max=20)
    public String password;
    @NotEmpty
    @Size(min=5, max=20)
    public String newPassword;

    public String confirmNewPassword;

    public ChangePasswordDTO() {
    }

    public String getPassword() {
        return password;
    }

    public ChangePasswordDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public ChangePasswordDTO setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
