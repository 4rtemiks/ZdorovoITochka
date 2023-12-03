package zdorovo.tochka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zdorovo.tochka.entity.UserState;
import zdorovo.tochka.repository.UserStateRepository;

@Service
public class UserStateService {

    @Autowired
    private UserStateRepository userStateRepository;

    public UserState save(UserState us) {
        return userStateRepository.save(us);
    }
    public UserState getUserState(Long chatId) {
        return userStateRepository.findByChatId(chatId);
    }
}
