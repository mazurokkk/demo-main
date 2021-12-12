package alla.verkhohliadova.demo_car.dto.response.excluded;

import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import alla.verkhohliadova.demo_car.entity.ProductCount;

@Getter
@Setter
public class ProductCountResponse {

    private Long id;
    @JsonProperty("product")
    private ProductResponse productResponse;
    private Integer count;

    public ProductCountResponse(ProductCount productCount) {
        id = productCount.getId();
        productResponse = new ProductResponse(productCount.getProduct());
        count = productCount.getCount();
    }
}
