package zdorovo.tochka.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import zdorovo.tochka.constant.CallbackType;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DishKeyboard extends BaseKeyboard {

    public InlineKeyboardMarkup getChooseDishMenu(String type) {
        InlineKeyboardButton todayDish = InlineKeyboardButton.builder()
                .text("\uD83C\uDF52 Блюда на сегодня")
                .callbackData(type + CallbackType.TODAY)
                .build();
        InlineKeyboardButton randomDish = InlineKeyboardButton.builder()
                .text("\uD83C\uDF4C Мне повезет!")
                .callbackData(type + CallbackType.RANDOM)
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        Collections.singletonList(todayDish),
                        Collections.singletonList(randomDish),
                        returnToMenuButton()
                ))
                .build();
    }

    public InlineKeyboardMarkup getRandomDish() {
        InlineKeyboardButton randomDish = InlineKeyboardButton.builder()
                .text("\uD83C\uDF4C Мне повезет!")
                .callbackData(CallbackType.DISH + CallbackType.RANDOM)
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboard(Arrays.asList(
                        Collections.singletonList(randomDish),
                        returnToMenuButton()
                ))
                .build();
    }

}
