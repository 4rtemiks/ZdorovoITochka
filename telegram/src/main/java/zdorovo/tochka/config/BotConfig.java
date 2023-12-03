package zdorovo.tochka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
public class BotConfig {
    @Value("${telegram.api.bot.name}")
    private String botName;

    @Value("${telegram.api.bot.token}")
    private String botToken;

}
