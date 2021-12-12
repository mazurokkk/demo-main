package alla.verkhohliadova.demo_car.entity;

import alla.verkhohliadova.demo_car.DemoCarApplication;
import alla.verkhohliadova.demo_car.dto.request.CategoryRequest;
import alla.verkhohliadova.demo_car.dto.response.CategoryResponse;
import alla.verkhohliadova.demo_car.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryTest {

    @Test
    public void getAllNames(){
        Category category_1 = new Category();

        Category category2 = new Category();


        //category1.setName("A КЛАС — MINI CARS");
        category2.setName("B КЛАС — SMALL CARS");


        List<Category> expected = new ArrayList<>();
        expected.add(CategoryService.Category_add(category_1));

        List<Category> actual = new ArrayList<>();
        //actual.add(CategoryService.Category_add(category_1));
        actual.add(category2);

        Assert.assertEquals(expected,actual);
    }
}