package bg.boxerclub.boxerclubbgrestserver.service.user;

import bg.boxerclub.boxerclubbgrestserver.model.BoxerClubUserDetails;
import bg.boxerclub.boxerclubbgrestserver.model.dto.user.*;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.VerificationToken;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

public interface UserService {
    UserDto registerNewUserAccount(RegisterUserDto registerUserDto, ServletWebRequest request);

    BoxerClubUserDetails authenticate(String userName);

    BoxerClubUserDetails login(String userName, String password);

    List<UserDto> getAllUsers();

    void deleteUser(Long id);

    UserDto getUserByVerificationToken(VerificationToken verificationToken);

    UserDto getUserByUserEmail(AuthRequest request);

    UserEntity getUserByUserEmail(String username);

    List<UserRoleDto> getAllRoles();

    UserDto editUser(EditUserDto userEditDto, Long id);

    EditUserDto getUser(Long id);

    void init();

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken getVerificationTokenByUser(UserEntity user);

    void createVerificationToken(UserDto user, String token);

    void saveRegisteredUser(UserDto userDto);

    boolean changePassword(UserChangePasswordDto userChangePasswordDto, BoxerClubUserDetails user);

    void forgottenPassword(AuthRequest authRequest, ServletWebRequest request);

    void setNewPassword(UserForgottenPasswordDto forgottenPasswordNewPasswordDto);

    void membership(AuthRequest authRequest, ServletWebRequest request);
}
