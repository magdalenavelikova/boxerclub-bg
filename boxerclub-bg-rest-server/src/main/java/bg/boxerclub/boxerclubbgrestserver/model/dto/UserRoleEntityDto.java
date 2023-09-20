package bg.boxerclub.boxerclubbgrestserver.model.dto;

public class UserRoleEntityDto {
    private String role;

    public UserRoleEntityDto() {
    }

    public String getRole() {
        return role;
    }

    public UserRoleEntityDto setRole(String role) {
        this.role = role;
        return this;
    }
}
