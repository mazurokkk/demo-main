package alla.verkhohliadova.demo_car.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class ProductCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Ordered ordered;

}
