package alla.verkhohliadova.demo_car.service;


import alla.verkhohliadova.demo_car.dto.request.PaginationRequest;
import alla.verkhohliadova.demo_car.dto.request.ProductRequest;
import alla.verkhohliadova.demo_car.dto.response.PageResponse;
import alla.verkhohliadova.demo_car.dto.response.ProductResponse;
import alla.verkhohliadova.demo_car.entity.*;
import alla.verkhohliadova.demo_car.repository.FavouriteRepository;
import alla.verkhohliadova.demo_car.repository.OrderedRepository;
import alla.verkhohliadova.demo_car.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    //private static Map<Integer, Product> allUsers;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private OrderedRepository orderedRepository;

    /*public static void setAllUsers(Map<Integer, Product> allUsers) {
        ProductService.allUsers = allUsers;
    }*/

    public void create(ProductRequest request, MultipartFile file) throws IOException {
        productRepository.save(productRequestToProduct(null, request, file));
    }

    /*public List<ProductResponse> findAllByCriteria(ProductCriteria criteria) {
        return productRepository.findAll(
                new ProductSpecification(criteria), criteria.getPaginationRequest().toPageable()
        ).stream().map(ProductResponse::new).collect(Collectors.toList());
    }*/

    public PageResponse<ProductResponse> findPage(PaginationRequest paginationRequest) {
        Page<Product> page = productRepository.findAll(paginationRequest.toPageable());
        return new PageResponse<>(page.getTotalPages(),
                page.getTotalElements(),
                page.get().map(ProductResponse::new).collect(Collectors.toList()));

    }

    public void update(Long id, ProductRequest request, MultipartFile file) throws IOException {
        productRepository.save(productRequestToProductUpdate(findOne(id), request, file));
    }

    private Product productRequestToProduct(Product product, ProductRequest request, MultipartFile file) throws IOException {
        if (product == null) {
            product = new Product();
        }
        Category category =
                categoryService.findOne(request.getCategoryId());
        product.setCategory(category);
        product.setModel(request.getModel());
        product.setTransmissionBox(request.getTransmissionBox());
        product.setNumberOfSeats(request.getNumberOfSeats());
        product.setYearOfIssue(request.getYearOfIssue());
        product.setPricePerDay(request.getPricePerDay());
        //product.setNumberOfDays(request.getNumberOfDays());
        product.setDescription(request.getDescription());

        File image = fileService.multipartFileConvertToFile(file);
        if (image.isFile()){
        String p = fileService.fileEncodeBase64(image);
            //System.out.println("\nb64: \n" + p);
            String path = fileService.saveFile(p);
            product.setImage(path);
        }
        return product;
    }

    private Product productRequestToProductUpdate(Product product, ProductRequest request, MultipartFile file) throws IOException {
        if (!request.getModel().isEmpty()) {
            product.setModel(request.getModel());
        }
        if (request.getTransmissionBox()== TransmissionBox.AUTOMATIC ||
                request.getTransmissionBox()==TransmissionBox.MANUAL){
            product.setTransmissionBox(request.getTransmissionBox());
        }
        if (request.getNumberOfSeats() != null){
            product.setNumberOfSeats(request.getNumberOfSeats());
        }
        if (request.getYearOfIssue() != null){
            product.setYearOfIssue(request.getYearOfIssue());
        }
        if (request.getPricePerDay() != null){
            product.setPricePerDay(request.getPricePerDay());
        }
        //if (request.getNumberOfDays() != null){
        //    product.setNumberOfDays(request.getNumberOfDays());
        //}
        if(!file.isEmpty()){
            File image = fileService.multipartFileConvertToFile(file);
            if (image.isFile()){
                String p = fileService.fileEncodeBase64(image);
                //System.out.println("\nb64: \n" + p);
                String path = fileService.saveFile(p);
                product.setImage(path);
            }
        }
        if(!request.getDescription().isEmpty()){
            product.setDescription(request.getDescription());
        }
        if(!request.getCategoryId().toString().isEmpty()) {
            Category category =
                    categoryService.findOne(request.getCategoryId());
            product.setCategory(category);
        }
        return product;
    }

    public Product findOne(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not exists"));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

    }

    public void delete(Long id) throws IllegalArgumentException {
        //Long favouriteId, orderedId;
        Product productGet = findOne(id);
        List <Favourite> allFavourites = favouriteRepository.findAll();
        List <Ordered> allOrderers = orderedRepository.findAll();
        for (Favourite favourite: allFavourites){
            if (favourite.getProduct() == productGet){
                favouriteRepository.delete(favourite);
            }
        }
        for (Ordered ordered : allOrderers){
            if (ordered.getProduct() == productGet){
                orderedRepository.delete(ordered);
            }
        }
        productRepository.delete(findOne(id));
    }
    /*public static List<Product> getAllProducts(){
        return new ArrayList<>(allUsers.values());
    }*/
}
