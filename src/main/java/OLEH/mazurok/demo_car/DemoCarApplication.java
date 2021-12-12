package alla.verkhohliadova.demo_car;


import alla.verkhohliadova.demo_car.entity.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoCarApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoCarApplication.class, args);

        //Category category_1 = new Category();
        //Category category2 = new Category();

        //category_1.setName("A КЛАС — MINI CARS");
        //category2.setName("B КЛАС — SMALL CARS");

        //List<Category> expected = new ArrayList<>();
        //expected.add(category1);
    }

}
