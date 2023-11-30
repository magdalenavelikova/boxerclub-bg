package bg.boxerclub.boxerclubbgrestserver.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class EditViewUserDto {

    private Long id;
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

    private String created;
    private List<UserRoleDto> roles;

    public EditViewUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public EditViewUserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public EditViewUserDto setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

    public EditViewUserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EditViewUserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public EditViewUserDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public EditViewUserDto setCity(String city) {
        this.city = city;
        return this;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public EditViewUserDto setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public EditViewUserDto setCreated(String created) {
        this.created = created;
        return this;
    }
}
