package alla.verkhohliadova.demo_car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.MapConstraints;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //@JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    /*private static Map<Long, Category> allNames;
    //private static int countId = 0;

    public static List<Category> getAllNames() {
        try {
            return new ArrayList<>(allNames.values());
        }catch (NullPointerException nullPointerException){
            System.out.println(nullPointerException);
        }
        return new ArrayList<>(allNames.values());
    }*/
}


