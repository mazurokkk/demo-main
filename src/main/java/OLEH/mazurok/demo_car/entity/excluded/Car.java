package alla.verkhohliadova.demo_car.entity.excluded;


import alla.verkhohliadova.demo_car.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "car")
    private User user;

    @OneToMany(mappedBy = "car")
    private List<ProductCount> productCounts = new ArrayList<>();

    public Car() {
    }
}
