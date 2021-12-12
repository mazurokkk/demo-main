package alla.verkhohliadova.demo_car.repository;

import alla.verkhohliadova.demo_car.entity.Car;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
