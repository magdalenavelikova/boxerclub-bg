package bg.boxerclub.boxerclubbgrestserver.service;

import bg.boxerclub.boxerclubbgrestserver.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }


}
