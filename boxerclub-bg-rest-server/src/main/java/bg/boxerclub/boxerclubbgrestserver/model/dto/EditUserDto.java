package bg.boxerclub.boxerclubbgrestserver.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class EditUserDto {
    Long id;
    @NotEmpty(message = "User email should be provided.")
    @Email(message = "Please enter valid email.")

    private String email;

    @NotEmpty
    @Size(min = 2, max = 30, message = "Please enter between 2 and 30 characters.")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Please enter between 2 and 30 characters.")
    private String lastName;
    private String country;
    private String city;

    private List<UserRoleDto> roles;

    public EditUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public EditUserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public EditUserDto setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public EditUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EditUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public EditUserDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public EditUserDto setCity(String city) {
        this.city = city;
        return this;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public EditUserDto setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
        return this;
    }
}
