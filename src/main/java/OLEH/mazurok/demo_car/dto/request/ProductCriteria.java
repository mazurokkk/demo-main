package alla.verkhohliadova.demo_car.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCriteria {
    private String name;
    private Long minPrice;
    private Long maxPrice;
    private Long categoryId;

    private PaginationRequest paginationRequest;
}
