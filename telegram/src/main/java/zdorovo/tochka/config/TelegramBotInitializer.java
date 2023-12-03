package zdorovo.tochka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import zdorovo.tochka.bot.TelegramBot;

@Component
public class TelegramBotInitializer {

    private final TelegramBot telegramBot;

    @Autowired
    public TelegramBotInitializer(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(telegramBot);
    }
}
