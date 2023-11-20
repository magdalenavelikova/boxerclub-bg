package bg.boxerclub.boxerclubbgrestserver.model.dto.user;

import bg.boxerclub.boxerclubbgrestserver.validation.annotation.FieldMatch;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "newPassword",
        second = "confirmPassword",
        message = "Passwords do not match!")
public class UserChangePasswordDto {
    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String oldPassword;
    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String newPassword;
    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String confirmNewPassword;

    public UserChangePasswordDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserChangePasswordDto setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserChangePasswordDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserChangePasswordDto setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
