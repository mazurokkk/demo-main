package alla.verkhohliadova.demo_car.service;

import alla.verkhohliadova.demo_car.dto.request.OrderedRequest;
import alla.verkhohliadova.demo_car.dto.response.OrderedResponse;
import alla.verkhohliadova.demo_car.entity.Ordered;
import alla.verkhohliadova.demo_car.entity.Product;
import alla.verkhohliadova.demo_car.entity.User;
import alla.verkhohliadova.demo_car.repository.OrderedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

   // @Autowired
   // private ProductCountRepository productCountRepository;

    @Autowired
    private OrderedRepository orderedRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public Ordered create(OrderedRequest request, Long userId, Long productId) {
        //Ordered ordered = ;
        return orderedRepository.save(orderedRequestToOrder(null, request, userId, productId));
       // List<ProductCount> productCounts = orderRequestToProductCounts(request, ordered);
       // productCountRepository.saveAll(productCounts);
    }

    /*private List<ProductCount> orderedRequestToProductCounts(OrderedRequest request, Ordered ordered) {
        return request.getProductCountRequests().stream().map(p -> productCountRequestToProductCount(p, ordered)).collect(Collectors.toList());
    }

    private ProductCount productCountRequestToProductCount(ProductCountRequest request, Ordered ordered) {
        return ProductCount.builder()
                .count(request.getCount())
                .product(productService.findOne(request.getProductId()))
                .ordered(ordered)
                .build();
    }*/

    private Ordered orderedRequestToOrder(Ordered ordered, OrderedRequest request, Long userId, Long productId) {
        if (ordered == null) {
            ordered = new Ordered();
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateStart = LocalDate.parse(request.getDateStart());//, formatter);
        LocalDate localDateFinish = LocalDate.parse(request.getDateFinish());//, formatter);

        User user = userService.findOne(userId);
        Product product = productService.findOne(productId);

        ordered.setDateStart(localDateStart);
        ordered.setTimeStart(request.getTimeStart());
        ordered.setDateFinish(localDateFinish);
        ordered.setTimeFinish(request.getTimeFinish());
        ordered.setFinished(request.getFinished());
        //ordered.setPrice(price);
        ordered.setPrice(request.getPrice());
        ordered.setProduct(product);
        ordered.setUser(user);
        //other fields
        return ordered;
    }

    /*public Ordered findOne(Long userId, Long productId){
        User userGet = userService.findOne(userId);
        Product productGet = productService.findOne(productId);
        List <Ordered> allOrderers = orderedRepository.findAll();
        Ordered ordered = new Ordered();
        //for (ordered:allOrderers){
            //if ()
        //}
        return ordered;
    }*/

    public List<OrderedResponse> findAll() {
        return orderedRepository.findAll().stream()
                .map(OrderedResponse::new)
                .collect(Collectors.toList());
    }

    public List<OrderedResponse> findAllUserOrderers(Long userId){
        User userGet = userService.findOne(userId);
        List <Ordered> orderedsForOneUser = new ArrayList<>();
        List <Ordered> all = orderedRepository.findAll();
        for (Ordered ordered:all){
            if(ordered.getUser() == userGet){
                orderedsForOneUser.add(ordered);
            }
        }
        return orderedsForOneUser.stream()
                .map(OrderedResponse::new)
                .collect(Collectors.toList());
    }
}
