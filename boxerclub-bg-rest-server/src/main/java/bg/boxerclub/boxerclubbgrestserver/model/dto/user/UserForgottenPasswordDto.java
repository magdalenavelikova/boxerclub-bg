package bg.boxerclub.boxerclubbgrestserver.model.dto.user;

import bg.boxerclub.boxerclubbgrestserver.validation.annotation.FieldMatch;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "Passwords do not match!")
public class UserForgottenPasswordDto {
    @NotEmpty
    private String verificationToken;

    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String password;

    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String confirmPassword;

    public UserForgottenPasswordDto() {
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public UserForgottenPasswordDto setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserForgottenPasswordDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserForgottenPasswordDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
