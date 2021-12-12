package alla.verkhohliadova.demo_car.repository;


import alla.verkhohliadova.demo_car.entity.Ordered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Ordered, Long> {
}
