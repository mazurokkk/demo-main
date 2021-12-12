package alla.verkhohliadova.demo_car.dto.response;

import alla.verkhohliadova.demo_car.entity.Product;
import alla.verkhohliadova.demo_car.entity.TransmissionBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String model;
    private TransmissionBox transmissionBox;
    private Integer numberOfSeats;
    private Integer yearOfIssue;
    private Long pricePerDay;
    //private Integer numberOfDays;
    private String description;
    private String image;
    private CategoryResponse category;

    public ProductResponse(Product product) {
        id = product.getId();
        model = product.getModel();
        transmissionBox = product.getTransmissionBox();
        numberOfSeats = product.getNumberOfSeats();
        yearOfIssue = product.getYearOfIssue();
        pricePerDay = product.getPricePerDay();
        //numberOfDays = product.getNumberOfDays();
        description = product.getDescription();
        image = product.getImage();
        category = new CategoryResponse(product.getCategory());
    }
}
