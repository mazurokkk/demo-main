package alla.verkhohliadova.demo_car.specification;

import org.springframework.data.jpa.domain.Specification;
import alla.verkhohliadova.demo_car.dto.request.ProductCriteria;
import alla.verkhohliadova.demo_car.entity.Category;
import alla.verkhohliadova.demo_car.entity.Product;

import javax.persistence.criteria.*;

public class ProductSpecification implements Specification<Product> {

    private String name;
    private Long minPrice;
    private Long maxPrice;
    private Long categoryId;

    public ProductSpecification(ProductCriteria criteria) {
        name = criteria.getName();
        minPrice = criteria.getMinPrice();
        maxPrice = criteria.getMaxPrice();
        categoryId = criteria.getCategoryId();
    }

    @Override
    public Predicate toPredicate(
            Root<Product> r, //join TABLE
            CriteriaQuery<?> cq,//select distinct, avg()
            CriteriaBuilder cb// where like FIELD, between, > <
    ) {
        Predicate byName = findByName(r, cb);
        Predicate byPrice = findByPrice(r, cb);
        Predicate byCategory = findByCategory(r, cb);
        return cb.and(byName, byPrice, byCategory);
    }

    private Predicate findByCategory(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (categoryId != null) {
            Join<Product, Category> category = r.join("category");
            predicate = cb.equal(category.get("id"), categoryId);
            //r.get("id")           Product.id
            //category.get("id)     Category.id
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByPrice(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (minPrice != null && maxPrice != null) {
            predicate = cb.between(r.get("price"), minPrice, maxPrice);
        } else if (minPrice != null) {
            predicate = cb.greaterThanOrEqualTo(r.get("price"), minPrice);
        } else if (maxPrice != null) {
            predicate = cb.lessThanOrEqualTo(r.get("price"), maxPrice);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByName(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (name != null) {
            predicate = cb.like(r.get("name"), '%' + name + '%');
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }
}
