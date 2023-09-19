package bg.boxerclub.boxerclubbgrestserver.model.dto;

import bg.boxerclub.boxerclubbgrestserver.model.validations.FieldMatch;
import bg.boxerclub.boxerclubbgrestserver.model.validations.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "Passwords do not match!")
public class UserRegisterDto {
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "Please enter valid email.")
    @UniqueUserEmail(message = "There is already a registered user with this email address.")
    private String email;
    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String password;
    @NotEmpty
    @Size(min = 5, max = 20, message = "Please enter between 5 and 20 characters.")
    private String confirmPassword;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Please enter between 2 and 30 characters.")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Please enter between 2 and 30 characters.")
    private String lastName;
    private String country;
    private String city;

    public UserRegisterDto() {

    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserRegisterDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserRegisterDto setCity(String city) {
        this.city = city;
        return this;
    }
}
