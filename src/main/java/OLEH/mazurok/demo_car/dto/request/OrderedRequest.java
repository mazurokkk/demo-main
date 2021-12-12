package alla.verkhohliadova.demo_car.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class OrderedRequest {
    private CharSequence dateStart;

    private LocalTime timeStart;

    private CharSequence dateFinish;

    private LocalTime timeFinish;

    private Long price;

    private Boolean finished;

    private Long userId;

    private Long productId;

    /*@NotEmpty
    @JsonProperty("products")
    private List<ProductCountRequest> productCountRequests;*/
}
