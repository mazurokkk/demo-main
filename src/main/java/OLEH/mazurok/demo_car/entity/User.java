package alla.verkhohliadova.demo_car.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean sex;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private UserRole userRole;

    @Column
    private String token;

    //@OneToOne
    //private Car car;

    @OneToMany(mappedBy = "user")
    private List<Ordered> orders = new ArrayList<>();

    @OneToMany (mappedBy = "user")
    private List<Favourite> favorites = new ArrayList<>();

}
