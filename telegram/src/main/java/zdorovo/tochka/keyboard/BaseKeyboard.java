package zdorovo.tochka.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import zdorovo.tochka.constant.CallbackType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaseKeyboard {

    public InlineKeyboardMarkup returnToMenuButtonKeyboard() {
        InlineKeyboardButton returnToMenu = new InlineKeyboardButton();

        returnToMenu.setText("<< В главное меню");
        returnToMenu.setCallbackData(CallbackType.TO_MAIN_MENU);
        return InlineKeyboardMarkup.builder()
                .keyboardRow(Collections.singletonList(returnToMenu))
                .build();
    }
    protected List<InlineKeyboardButton> returnToMenuButton() {
        InlineKeyboardButton returnToMenu = new InlineKeyboardButton();

        returnToMenu.setText("<< В главное меню");
        returnToMenu.setCallbackData(CallbackType.TO_MAIN_MENU);
        return Collections.singletonList(returnToMenu);
    }

    protected List<InlineKeyboardButton> returnBackAndToMenu(String type) {
        return Arrays.asList(returnBackButton(type).get(0), returnToMenuButton().get(0));
    }
    protected List<InlineKeyboardButton> returnBackAndToMenu(String type, int stepsBack) {
        return Arrays.asList(returnBackButton(type, stepsBack).get(0), returnToMenuButton().get(0));
    }

    protected List<InlineKeyboardButton> returnBackButton(String type) {
        return returnBackButton(type, 1);
    }
    protected List<InlineKeyboardButton> returnBackButton(String type, int stepsBack) {
        InlineKeyboardButton returnBackButton = new InlineKeyboardButton();

        String[] splitted = type.split(";");

        StringBuilder newType = new StringBuilder();
        if(splitted.length <=1) {
            newType.append(CallbackType.TO_MAIN_MENU);
        } else {
            for (int i = 0; i < splitted.length - stepsBack; i++)
                newType.append(splitted[i]).append(";");
        }
        returnBackButton.setText("< Назад");
        returnBackButton.setCallbackData(newType.toString());
        return Collections.singletonList(returnBackButton);
    }

    public InlineKeyboardMarkup getOnlyBack(String type) {
        return getOnlyBack(type, 1);
    }
    public InlineKeyboardMarkup getOnlyBack(String type, int stepsBack) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(returnBackButton(type, stepsBack));

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(rows);
        return keyboard;
    }

}
