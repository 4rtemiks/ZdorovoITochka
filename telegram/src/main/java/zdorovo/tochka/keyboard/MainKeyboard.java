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
                .callbackData(CallbackType.TO_MAIN_MENU)
                .build();

        InlineKeyboardButton profile = InlineKeyboardButton.builder()
                .text("⚙️ Настройки")
                .callbackData(CallbackType.TO_MAIN_MENU)
                .build();

        InlineKeyboardButton food = InlineKeyboardButton.builder()
                .text("\uD83E\uDED3 Показать блюдо")
                .callbackData(CallbackType.TO_MAIN_MENU)
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
