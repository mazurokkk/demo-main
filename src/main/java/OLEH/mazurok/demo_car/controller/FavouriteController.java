package alla.verkhohliadova.demo_car.controller;

import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.security.JwtTokenTool;
import alla.verkhohliadova.demo_car.service.FavoriteService;
import alla.verkhohliadova.demo_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/favourites")
public class FavouriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenTool jwtTokenTool;

    @GetMapping("/add/{userToken}/{productId}")
    public ModelAndView add(@PathVariable ("userToken") String userToken,
                            @PathVariable ("productId") Long productId){
        System.err.println("added");
        Long userId = userService.findUserIdByToken(userToken);
        favoriteService.create(userId, productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.setViewName("redirect: /catalog");
        return modelAndView;
    }

    @GetMapping("/allUserFavourites/{userToken}")
    public ModelAndView allUserFavourites(@PathVariable ("userToken") String userToken){
        //Long userId = userService.findUserIdByToken(userToken);
        //String username = jwtTokenTool.getUsername(userToken);
        //User user = userService.findByUsername(
        //        jwtTokenTool.getUsername(userToken));
        Long userId = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getId();
        List<ProductResponse> allUserFavouritesCar = favoriteService.findAllFavouriteForUser(userId);
        //System.err.println(allUserFavouritesCar);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allFavouriteCars", allUserFavouritesCar);
        modelAndView.setViewName("html/userFavourites");
        return modelAndView;
    }

    @GetMapping("/delete/{userToken}/{productId}")
    public ModelAndView delete(@PathVariable ("userToken") String userToken,
                               @PathVariable ("productId") Long productId){
        System.err.println("deleted");
        Long userId = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getId();
        favoriteService.delete(userId, productId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.setViewName("redirect: /userFavourites");
        return modelAndView;
    }
}
