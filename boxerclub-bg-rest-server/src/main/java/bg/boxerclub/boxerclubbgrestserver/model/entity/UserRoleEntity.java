package bg.boxerclub.boxerclubbgrestserver.model.entity;

import bg.boxerclub.boxerclubbgrestserver.model.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "name")
    private Role role;

    public UserRoleEntity() {
    }

    public Role getRole() {
        return role;
    }

    public UserRoleEntity setRole(Role role) {
        this.role = role;
        return this;
    }
}
