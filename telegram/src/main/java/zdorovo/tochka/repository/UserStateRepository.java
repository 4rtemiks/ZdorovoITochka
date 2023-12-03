package zdorovo.tochka.repository;

import org.springframework.data.repository.CrudRepository;
import zdorovo.tochka.entity.UserState;

public interface UserStateRepository extends CrudRepository<UserState, Long> {

    UserState findByChatId(Long chatId);

}
