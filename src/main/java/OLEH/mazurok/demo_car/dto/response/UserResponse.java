package alla.verkhohliadova.demo_car.dto.response;

import alla.verkhohliadova.demo_car.entity.User;
import alla.verkhohliadova.demo_car.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String firstname;
    private String surname;
    private String username;
    private String password;
    private Boolean sex;
    private String email;
    private String phone;
    private UserRole user_role;
    private String token;

    public UserResponse(User user) {
        id = user.getId();
        firstname = user.getFirstname();
        surname = user.getSurname();
        username = user.getUsername();
        password = user.getPassword();
        sex = user.getSex();
        email = user.getEmail();
        phone = user.getPhone();
        user_role = user.getUserRole();
        token = user.getToken();
    }
}
