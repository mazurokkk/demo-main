package alla.verkhohliadova.demo_car.dto.response;

import alla.verkhohliadova.demo_car.entity.Favourite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteResponse {
    private Long id;
    private UserResponse user;
    private ProductResponse product;

    public FavouriteResponse(Favourite favourite){
        id = favourite.getId();
        user = new UserResponse(favourite.getUser());
        product = new ProductResponse(favourite.getProduct());
    }
}
