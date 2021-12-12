package alla.verkhohliadova.demo_car.dto.response;

import alla.verkhohliadova.demo_car.entity.Ordered;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OrderedResponse {
    private Long id;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateStart;
    private LocalTime timeStart;
    private LocalDate dateFinish;
    private LocalTime timeFinish;
    private Long price;
    private Boolean finished;
    private UserResponse user;
    private ProductResponse product;

    public OrderedResponse(Ordered ordered){
        id = ordered.getId();
        dateStart = ordered.getDateStart();
        timeStart = ordered.getTimeStart();
        dateFinish = ordered.getDateFinish();
        timeFinish = ordered.getTimeFinish();
        price = ordered.getPrice();
        finished = ordered.getFinished();
        user = new UserResponse(ordered.getUser());
        product = new ProductResponse(ordered.getProduct());
    }
}
