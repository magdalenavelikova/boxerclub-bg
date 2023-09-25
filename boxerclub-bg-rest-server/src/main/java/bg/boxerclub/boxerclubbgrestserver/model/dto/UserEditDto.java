package bg.boxerclub.boxerclubbgrestserver.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserEditDto {
    Long id;
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "Please enter valid email.")

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

    @NotEmpty
    private Boolean role_Admin;
    @NotEmpty
    private Boolean role_Moderator;
    @NotEmpty
    private Boolean role_Member;
    @NotEmpty
    private Boolean role_User;

    public UserEditDto() {
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public UserEditDto setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEditDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEditDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserEditDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserEditDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserEditDto setCity(String city) {
        this.city = city;
        return this;
    }

    public Boolean getRole_Admin() {
        return role_Admin;
    }

    public UserEditDto setRole_Admin(Boolean role_Admin) {
        this.role_Admin = role_Admin;
        return this;
    }

    public Boolean getRole_Moderator() {
        return role_Moderator;
    }

    public UserEditDto setRole_Moderator(Boolean role_Moderator) {
        this.role_Moderator = role_Moderator;
        return this;
    }

    public Boolean getRole_Member() {
        return role_Member;
    }

    public UserEditDto setRole_Member(Boolean role_Member) {
        this.role_Member = role_Member;
        return this;
    }

    public Boolean getRole_User() {
        return role_User;
    }

    public UserEditDto setRole_User(Boolean role_User) {
        this.role_User = role_User;
        return this;
    }
}
