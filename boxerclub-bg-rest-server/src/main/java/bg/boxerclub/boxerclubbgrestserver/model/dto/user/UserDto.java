package bg.boxerclub.boxerclubbgrestserver.model.dto.user;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String created;
    private List<UserRoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public UserDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public UserDto setCreated(String created) {
        this.created = created;
        return this;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public UserDto setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
        return this;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (getFirstName() != null) {
            fullName.append(getFirstName());
        }
        if (getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }
}
