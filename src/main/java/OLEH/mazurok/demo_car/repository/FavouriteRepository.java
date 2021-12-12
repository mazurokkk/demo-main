package alla.verkhohliadova.demo_car.repository;

import alla.verkhohliadova.demo_car.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long>, JpaSpecificationExecutor<Favourite> {
}
