package zdorovo.tochka.repository;

import org.springframework.data.repository.CrudRepository;
import zdorovo.tochka.entity.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {
}
