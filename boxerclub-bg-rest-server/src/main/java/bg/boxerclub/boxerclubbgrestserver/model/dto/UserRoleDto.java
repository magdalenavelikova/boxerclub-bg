package bg.boxerclub.boxerclubbgrestserver.model.dto;

public class UserRoleDto {
    private String role;

    public UserRoleDto() {
    }

    public String getRole() {
        return role;
    }

    public UserRoleDto setRole(String role) {
        this.role = role;
        return this;
    }
}
