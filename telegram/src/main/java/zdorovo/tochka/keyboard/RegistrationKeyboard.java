package zdorovo.tochka.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import zdorovo.tochka.constant.CallbackType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class RegistrationKeyboard extends BaseKeyboard {

    public InlineKeyboardMarkup getRegistrationMenu(BigDecimal height, BigDecimal weight) {
        String heightText = "✍️ Заполнить рост";
        if (height != null)
            heightText = "✍️ Рост: " + height.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        String weightText = "✍️ Заполнить вес";
        if (weight != null)
            weightText = "✍️ Вес: " + weight.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

        InlineKeyboardButton heightButton = InlineKeyboardButton.builder()
                .text(heightText)
                .callbackData(CallbackType.REGISTER + CallbackType.HEIGHT)
                .build();
        InlineKeyboardButton weightButton = InlineKeyboardButton.builder()
                .text(weightText)
                .callbackData(CallbackType.REGISTER + CallbackType.WEIGHT)
                .build();
        InlineKeyboardButton confirmButton = InlineKeyboardButton.builder()
                .text("✅ Создать аккаунт")
                .callbackData(CallbackType.REGISTER + CallbackType.CONFIRM)
                .build();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(Collections.singletonList(heightButton));
        buttons.add(Collections.singletonList(weightButton));
        if (height != null & weight != null)
            buttons.add(Collections.singletonList(confirmButton));

        return InlineKeyboardMarkup.builder()
                .keyboard(buttons)
                .build();
    }
}
