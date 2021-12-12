package alla.verkhohliadova.demo_car.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotEmpty
    @JsonProperty("products")
    private List<ProductCountRequest> productCountRequests;
}
