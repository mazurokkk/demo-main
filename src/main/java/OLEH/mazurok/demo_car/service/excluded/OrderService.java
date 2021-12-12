package alla.verkhohliadova.demo_car.service.excluded;

import alla.verkhohliadova.demo_car.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alla.verkhohliadova.demo_car.dto.request.OrderRequest;
import alla.verkhohliadova.demo_car.dto.request.ProductCountRequest;
import alla.verkhohliadova.demo_car.entity.Ordered;
import alla.verkhohliadova.demo_car.entity.ProductCount;
import alla.verkhohliadova.demo_car.repository.OrderRepository;
import alla.verkhohliadova.demo_car.repository.ProductCountRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

   // @Autowired
   // private ProductCountRepository productCountRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public void create(OrderRequest request) {
        Ordered ordered = orderRequestToOrder(null, request);
        ordered = orderRepository.saveAndFlush(ordered);
       // List<ProductCount> productCounts = orderRequestToProductCounts(request, ordered);
       // productCountRepository.saveAll(productCounts);
    }

    private List<ProductCount> orderRequestToProductCounts(OrderRequest request, Ordered ordered) {
        return request.getProductCountRequests().stream().map(p -> productCountRequestToProductCount(p, ordered)).collect(Collectors.toList());
    }

    private ProductCount productCountRequestToProductCount(ProductCountRequest request, Ordered ordered) {
        return ProductCount.builder()
                .count(request.getCount())
                .product(productService.findOne(request.getProductId()))
                .ordered(ordered)
                .build();
    }

    private Ordered orderRequestToOrder(Ordered ordered, OrderRequest request) {
        if (ordered == null) {
            ordered = new Ordered();
            ordered.setDate(LocalDate.now());
            ordered.setTime(LocalTime.now());
            ordered.setFinished(false);
        }
        //other fields
        return ordered;
    }
}
