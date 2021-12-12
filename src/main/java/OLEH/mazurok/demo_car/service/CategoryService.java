package alla.verkhohliadova.demo_car.service;

import alla.verkhohliadova.demo_car.dto.request.CategoryRequest;
import alla.verkhohliadova.demo_car.dto.response.CategoryResponse;
import alla.verkhohliadova.demo_car.entity.Category;
import alla.verkhohliadova.demo_car.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void create(CategoryRequest request) {
        categoryRepository.save(categoryRequestToCategory(null, request));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }



    public void update(Long id, CategoryRequest request) {
        categoryRepository.save(categoryRequestToCategory(findOne(id), request));
    } // не потрібно

    public void delete(Long id) {
        categoryRepository.delete(findOne(id));
    }


    private Category categoryRequestToCategory(Category category, CategoryRequest request) {
        if (category == null) {
            category = new Category();
        }
        category.setName(request.getName());
        return category;
    }

    public static Category Category_add(Category category_1){
        //Category category_1 = new Category();
        category_1.setName("A КЛАС — MINI CARS");
        return category_1;
    }

    public Category findOne(Long id){// throws IllegalArgumentException {
        return categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not exists"));
    }
}

    /*private static Map<Long, Category> allNames;
    //private static int countId = 0;

    public static List<Category> getAllNames() {
        try {
            return new ArrayList<>(allNames.values());
        }catch (NullPointerException nullPointerException){
            System.out.println(nullPointerException);
        }
        return new ArrayList<>(allNames.values());
    }
}*/
