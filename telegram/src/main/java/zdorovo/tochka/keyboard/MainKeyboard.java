package zdorovo.tochka.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import zdorovo.tochka.constant.CallbackType;

import java.util.Arrays;
import java.util.Collections;

@Component
public class MainKeyboard extends BaseKeyboard {

    public InlineKeyboardMarkup getBaseMenu() {
        InlineKeyboardButton calories = InlineKeyboardButton.builder()
                .text("\uD83C\uDF4E Расчитать калории")
                .callbackData(CallbackType.COUNT_CALORIES)
                .build();

        InlineKeyboardButton profile = InlineKeyboardButton.builder()
                .text("⚙️ Настройки")
                .callbackData(CallbackType.SETTINGS)
                .build();

        InlineKeyboardButton food = InlineKeyboardButton.builder()
                .text("\uD83E\uDED3 Показать блюдо")
                .callbackData(CallbackType.DISH)
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        Collections.singletonList(calories),
                        Collections.singletonList(profile),
                        Collections.singletonList(food)
                ))
                .build();
    }
}
