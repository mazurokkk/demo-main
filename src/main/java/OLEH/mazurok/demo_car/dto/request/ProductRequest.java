package alla.verkhohliadova.demo_car.dto.request;

import alla.verkhohliadova.demo_car.entity.TransmissionBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    //@NotBlank
    private String model;

    private TransmissionBox transmissionBox;

    private Integer numberOfSeats;

    private Integer yearOfIssue;

    //@Positive (message = "Значення має бути позитивним")
    //@NotBlank
    private Long pricePerDay;

    //@NotBlank
    //private Integer numberOfDays;

    //@NotBlank
    private String description;

    private String image;

    private Long categoryId;

}
