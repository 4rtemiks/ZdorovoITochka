package zdorovo.tochka.repository;

import org.springframework.data.repository.CrudRepository;
import zdorovo.tochka.entity.Ingridient;

public interface IngridientRepository extends CrudRepository<Ingridient, Long> {
}
