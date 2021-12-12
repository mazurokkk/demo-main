package alla.verkhohliadova.demo_car.repository;


import alla.verkhohliadova.demo_car.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Optional<Category> findByName(String name);
    //Optional<Category> findById(Long id);
    //Category findById(Long id);
    //Optional<User> findByUsername (String username);
    //User findUserById(Long id);
}
