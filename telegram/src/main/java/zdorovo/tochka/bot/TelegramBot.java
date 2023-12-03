package zdorovo.tochka.bot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import zdorovo.tochka.handler.MainHandler;
import zdorovo.tochka.config.BotConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfig config;
    @Autowired
    @Lazy
    private MainHandler mainHandler;

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Override
    public void onUpdateReceived(Update update) {
        executorService.submit(() -> {
            try {
                mainHandler.handleUpdate(update);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }




    public String sendMessage(String userId, String text) {
        SendMessage sms = new SendMessage();
        sms.setText(text);
        sms.setChatId(userId);
        sms.enableHtml(true);
        sms.disableWebPagePreview();
        try{
            execute(sms);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "SEND SUCCESSFULLY";
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
