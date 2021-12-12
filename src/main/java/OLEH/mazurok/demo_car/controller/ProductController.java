package alla.verkhohliadova.demo_car.controller;

import alla.verkhohliadova.demo_car.dto.request.ProductRequest;
import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.service.CategoryService;
import alla.verkhohliadova.demo_car.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("**/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/add/{userToken}", consumes = {"multipart/form-data"})
    public ModelAndView create(@PathVariable ("userToken") String userToken, ProductRequest productRequest, @RequestParam("file") MultipartFile file) throws Exception{
        System.out.println("CONTROLLER");
        productService.create(productRequest, file);
        List<ProductResponse> allCars = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userToken", userToken);
        modelAndView.addObject("message", "Новий об'єкт успішно додано");
        modelAndView.addObject("allCars", allCars);
        modelAndView.setViewName("html/allCars");
        return modelAndView;
    }

    @PostMapping(value = "/updated/{id}", consumes = {"multipart/form-data"})
    public ModelAndView update(@PathVariable("id") Long id, ProductRequest productRequest, @RequestParam("file") MultipartFile file) throws IOException {
        productService.update(id, productRequest, file);
        List<ProductResponse> allCars = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Дані успішно оновлено");
        modelAndView.addObject("allCars", allCars);
        modelAndView.setViewName("html/allCars");
        return modelAndView;
    }

}
