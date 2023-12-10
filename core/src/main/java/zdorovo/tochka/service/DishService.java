package zdorovo.tochka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdorovo.tochka.entity.Dish;
import zdorovo.tochka.repository.DishRepository;

import java.util.Random;

@Slf4j
@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish findOne(Long id) {
        return dishRepository.findById(id).orElse(null);
    }

    public Dish findRandom() {
        Random rand = new Random();
        return findOne(rand.nextLong(dishRepository.count()) + 1);
    }

}
