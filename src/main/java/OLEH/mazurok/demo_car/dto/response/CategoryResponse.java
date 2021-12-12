package alla.verkhohliadova.demo_car.dto.response;

import lombok.Getter;
import lombok.Setter;
import alla.verkhohliadova.demo_car.entity.Category;

import java.util.Map;

@Setter
@Getter
public class CategoryResponse {
    private String name;
    private Long id;

    public CategoryResponse(Category category) {
        name = category.getName();
        id = category.getId();
    }
}
