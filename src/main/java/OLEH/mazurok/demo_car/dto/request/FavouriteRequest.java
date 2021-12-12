package alla.verkhohliadova.demo_car.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteRequest {
    private Long userId;

    private Long productId;
}
