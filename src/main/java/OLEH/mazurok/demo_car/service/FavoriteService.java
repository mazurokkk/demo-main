package alla.verkhohliadova.demo_car.service;

import alla.verkhohliadova.demo_car.dto.response.FavouriteResponse;
import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.entity.Favourite;
import alla.verkhohliadova.demo_car.entity.Product;
import alla.verkhohliadova.demo_car.entity.User;
import alla.verkhohliadova.demo_car.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public void create(Long userId, Long productId){
        favouriteRepository.save(favouriteRequestToFavourite(userId, productId));
    }

    private Favourite favouriteRequestToFavourite(Long userId, Long productId){
        Favourite newFavourite = null;
        int count = 0;
        User user = userService.findOne(userId);
        Product product = productService.findOne(productId);
        List <Favourite> all = favouriteRepository.findAll();
        for (Favourite favourite : all){
            if (favourite.getUser()==user){
                if (favourite.getProduct() == product){
                    newFavourite = favourite;
                    count ++;
                    System.err.println("was added earlier");
                }
            }
        }
        if (count == 0){
            newFavourite = new Favourite();
            newFavourite.setUser(user);
            newFavourite.setProduct(product);
            System.err.println("added finally");
        }
        return newFavourite;
    }

    public List<FavouriteResponse> findAll(){
        return favouriteRepository.findAll()
                .stream().map(FavouriteResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAllFavouriteForUser (Long userId){
        User userGet = userService.findOne(userId);
        List <Favourite> favouriteForOneUser = new ArrayList<>();
        List <Favourite> all = favouriteRepository.findAll();
        for (Favourite favourite: all){
            if(favourite.getUser() == userGet){
                favouriteForOneUser.add(favourite);
            }
        }
        List <Product> allforUser = new ArrayList<>();
        Product product;
        for (Favourite favourite : favouriteForOneUser){
            product = favourite.getProduct();
            allforUser.add(product);
        }
        return allforUser.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public void delete (Long userId, Long productId){
        Long favouriteId;
        User userGet = userService.findOne(userId);
        Product productGet = productService.findOne(productId);
        List <Favourite> all = favouriteRepository.findAll();
        for (Favourite favourite : all){
            if(favourite.getUser() == userGet && favourite.getProduct() == productGet){
                favouriteId = favourite.getId();
                favouriteRepository.deleteById(favouriteId);
            }
        }
    }
    //public
}
