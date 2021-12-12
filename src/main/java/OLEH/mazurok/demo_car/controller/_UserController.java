package alla.verkhohliadova.demo_car.controller;

import alla.verkhohliadova.demo_car.dto.request.UserRequest;
import alla.verkhohliadova.demo_car.dto.response.CategoryResponse;
import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.entity.Product;
import alla.verkhohliadova.demo_car.entity.User;
import alla.verkhohliadova.demo_car.entity.UserRole;
import alla.verkhohliadova.demo_car.repository.UserRepository;
import alla.verkhohliadova.demo_car.security.JwtTokenTool;
import alla.verkhohliadova.demo_car.service.CategoryService;
import alla.verkhohliadova.demo_car.service.ProductService;
import alla.verkhohliadova.demo_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/users")
public class _UserController {
    @Autowired
    private JwtTokenTool jwtTokenTool;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/registered")
    //public String create(@RequestBody UserRequest userRequest) {
    public ModelAndView create(UserRequest userRequest) throws Exception{
        boolean successes = userService.register(userRequest);
        ModelAndView modelAndView = new ModelAndView();
        if (successes == true) {
            modelAndView.addObject("message", "Реєстрація пройшла успішно, увійдіть, будь ласка, до свого облікового запису");
            modelAndView.setViewName("html/login");
        }
        else {
            modelAndView.addObject("error", "Придумайте інший логін");
            modelAndView.setViewName("html/newUser");
        }
        return modelAndView;
    }


   @PostMapping("/in")
   public ModelAndView log(@RequestParam String username, UserRequest userRequest) {
       UserRole userRole = userService.findUserRoleByUsername(userRequest);
       User user = userService.findByUsername(username);
       ModelAndView modelAndView = new ModelAndView();
       try {
           userService.login(userRequest);
           String userToken = user.getToken();
           if (userRole == UserRole.ROLE_ADMIN) {
               modelAndView.addObject("userToken", userToken);
               modelAndView.addObject("username", username);
               modelAndView.setViewName("html/homePageForAdmin");
           }
           else{
               modelAndView.addObject("userToken", userToken);
               modelAndView.addObject("username", username);
               modelAndView.setViewName("html/homePageForUser");
           }
           return modelAndView;
       }catch (Exception e){
           modelAndView.addObject("error", "Неправильно введено логін або пароль");
           modelAndView.setViewName("html/login");
           return modelAndView;
       }
    }

    @GetMapping("/catalog/{userToken}")
    @ResponseBody
    public ModelAndView catalog(@PathVariable ("userToken") String userToken){
        //User user = userService.findByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
        List<ProductResponse> allCars = productService.findAll();
        modelAndView.setViewName("html/catalog");
        System.err.println("/catalog/{userToken}\t" + userToken);
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("allCars", allCars);
        return modelAndView;
    }

    @GetMapping("/catalog/{userToken}/car/{productId}")
    public ModelAndView oneCar(@PathVariable("userToken") String userToken, @PathVariable("productId") Long productId){
        Product product = productService.findOne(productId);
        List<CategoryResponse> allCategories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        System.err.println("**/catalog{userId}/car/{productId}\t" + userToken + " / " + productId);
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("product", product);
        modelAndView.addObject("allCategories", allCategories);
        modelAndView.setViewName("html/Car");
        return modelAndView;
    }

    @GetMapping("/update/{userToken}")
    public ModelAndView pageToUpdateProfile(@PathVariable("userToken") String userToken){//, UserRequest userRequest){
        Long userId = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getId();
        User user = userService.findOne(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("html/updateProfile");
        return modelAndView;
    }

    @GetMapping("/updated/{userToken}")
    public ModelAndView updateProfile(@PathVariable("userToken") String userToken, UserRequest userRequest){
        Long userId = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getId();
        String username = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getUsername();
        userService.update(userId, userRequest);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("username", username);
        System.err.println(username + "\n" + userToken);
        modelAndView.addObject("message", "Дані успішно оновлено");
        modelAndView.setViewName("html/homePageForUser");
        return modelAndView;
    }

    @GetMapping("/home/{userToken}")
    public ModelAndView goToCabinet(@PathVariable("userToken") String userToken){
        String username = userService.findByUsername(jwtTokenTool.getUsername(userToken)).getUsername();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", username);
        modelAndView.addObject("userToken", userToken);
        modelAndView.setViewName("html/homePageForUser");
        return modelAndView;
    }

    @GetMapping("/out/{userToken}")
    public ModelAndView goOut(@PathVariable("userToken") String userToken){
        User user = userService.findByToken(userToken);
        //String newToken = null;
        user.setToken(null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect: /catalog");
        return modelAndView;
    }


    /*@GetMapping("/list")
    public Iterable<User> getCustomers() {
        return userRepository.findAll();
    }*/

    /*@GetMapping("/find/{id}")
    public User findCustomerById(@PathVariable Long id) {
        return userRepository.findUserById(id);
    }*/



    /*@RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView showForm(){
        return new ModelAndView("userHome", "user", new User());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("user") User user,
                         BindingResult result, ModelMap model){
        if(result.hasErrors()){
            return "error";
        }
        model.addAttribute(("username"), user.getUsername());
        model.addAttribute(("password"), user.getPassword());
        return "userView";
    }*/
}
