package alla.verkhohliadova.demo_car.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Ordered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateStart;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh/MM")
    private LocalTime timeStart;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateFinish;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh/MM")
    private LocalTime timeFinish;

    @Column
    private Long price;

    @Column
    private Boolean finished;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    //@OneToMany(mappedBy = "ordered")
    //private List<ProductCount> productCounts = new ArrayList<>();

}
