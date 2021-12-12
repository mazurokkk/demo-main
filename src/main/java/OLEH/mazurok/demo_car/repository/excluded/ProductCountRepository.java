package alla.verkhohliadova.demo_car.repository.excluded;


import alla.verkhohliadova.demo_car.entity.ProductCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCount, Long> {
}
