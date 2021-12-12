package alla.verkhohliadova.demo_car.dto.request;

import alla.verkhohliadova.demo_car.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String firstname;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @Size(min = 3, max = 30)
    private String password;

    private Boolean sex;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private String token;

//    @NotEmpty//@Size(min = 1)
    private List<Long> favoritesIds;
    private UserRole userRole;
}
