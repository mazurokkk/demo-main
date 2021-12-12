package alla.verkhohliadova.demo_car.controller;

import alla.verkhohliadova.demo_car.dto.response.CategoryResponse;
import alla.verkhohliadova.demo_car.dto.response.OrderedResponse;
import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.dto.response.UserResponse;
import alla.verkhohliadova.demo_car.entity.Product;
import alla.verkhohliadova.demo_car.security.JwtTokenTool;
import alla.verkhohliadova.demo_car.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//import java.util.Map;

@Controller
public class WebController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    JwtTokenTool jwtTokenTool;

    @GetMapping("/signUp")
    public String newUser(){
        return "html/newUser";
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        //List<UserResponse> allUsers = userService.findAll();
        //modelAndView.addObject("allUsers", allUsers);
        modelAndView.setViewName("html/login");
        return modelAndView;
    }

    @GetMapping("**/catalog")
    public ModelAndView catalog(@RequestParam (name = "userToken",required = false) String userToken){//@RequestParam (required = false) String username){
        List<ProductResponse> allCars = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/catalog");
        //System.err.println(username + "username");
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allCars", allCars);
        return modelAndView;
    }

    @GetMapping("**/catalog/car/{id}")
    public ModelAndView oneCar(@RequestParam (required = false, name = "token") String token, @PathVariable("id") Long id){
        Product product = productService.findOne(id);
        List<CategoryResponse> allCategories = categoryService.findAll();
        //Long userId = userService.findUserIdByToken(token);
        //redirectAttributes.addAttribute("token", token);
        ModelAndView modelAndView = new ModelAndView();
        //System.err.println("**/catalog/car/{id}\t" + userId);
        //modelAndView.addObject("userId", userId);
        modelAndView.setViewName("html/Car");
        modelAndView.addObject("product", product);
        modelAndView.addObject("allCategories", allCategories);
        return modelAndView;
    }

    @GetMapping("**/allUsers/{userToken}")
    public ModelAndView getAllUsers(@PathVariable ("userToken") String userToken) {
        ModelAndView modelAndView = new ModelAndView();
        List<UserResponse> allUsers = userService.findAll();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allUsers", allUsers);
        modelAndView.setViewName("html/allUsers");
        return modelAndView;
    }

    @GetMapping("**/allCars/{userToken}")
    public ModelAndView getAllCars(@PathVariable ("userToken") String userToken) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductResponse> allCars = productService.findAll();
        modelAndView.addObject("userToken", userToken);
        modelAndView.setViewName("html/allCars");
        modelAndView.addObject("allCars", allCars);
        return modelAndView;
    }

    @GetMapping("**/add/{userToken}")
    public ModelAndView newCar(@PathVariable ("userToken") String userToken){
        List<CategoryResponse> allCategories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allCategories", allCategories);
        modelAndView.setViewName("html/newCar");
        return modelAndView;
    }

    @GetMapping("**/allOrders/{userToken}")
    public ModelAndView getAllOrderers(@PathVariable("userToken") String userToken){
        List<OrderedResponse> allOrders = orderService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allOrders", allOrders);
        modelAndView.setViewName("html/allOrders");
        return modelAndView;
    }

    @GetMapping("**/allCategories/{userToken}")
    public ModelAndView getAllCategories(@PathVariable ("userToken") String userToken){
        List<CategoryResponse> allCategories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allCategories", allCategories);
        modelAndView.setViewName("html/allCategories");
        return modelAndView;
    }

    @GetMapping("**/addCategory")
    public ModelAndView newCategory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/newCategory");
        return modelAndView;
    }

    @GetMapping("**/userFavourites")
    public ModelAndView getAllUserFavourites(@RequestParam ("userToken") String userToken){
        Long userId = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getId();
        List<ProductResponse> allUserFavouritesCar = favoriteService.findAllFavouriteForUser(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allFavouriteCars", allUserFavouritesCar);
        modelAndView.setViewName("html/userFavourites");
        return modelAndView;
    }

    @GetMapping("/rules/{userToken}")
    public ModelAndView pageRulesForAutorisedUser(@PathVariable ("userToken") String userToken){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.setViewName("html/rules");
        return modelAndView;
    }

    @GetMapping("/rules")
    public ModelAndView pageRules(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("html/rules");
        return modelAndView;
    }
}
