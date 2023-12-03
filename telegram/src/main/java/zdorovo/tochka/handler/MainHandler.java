package zdorovo.tochka.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainHandler extends BaseHandler {

    public void handleUpdate(Update update) {
        Long chatId = update.getMessage().getChatId();
        sendWarningMessage(chatId, "Hello world!");
    }

}
