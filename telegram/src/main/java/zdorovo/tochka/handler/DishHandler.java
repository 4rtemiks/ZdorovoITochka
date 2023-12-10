package zdorovo.tochka.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import zdorovo.tochka.constant.CallbackType;
import zdorovo.tochka.entity.Dish;
import zdorovo.tochka.entity.Member;
import zdorovo.tochka.entity.UserState;
import zdorovo.tochka.keyboard.DishKeyboard;
import zdorovo.tochka.service.DishService;
import zdorovo.tochka.service.UserStateService;

import java.io.File;
import java.util.stream.Collectors;

@Component
public class DishHandler extends BaseHandler {

    @Autowired
    private DishService dishService;
    @Autowired
    private UserStateService userStateService;

    @Autowired
    private DishKeyboard dishKeyboard;

    public void handleCallback(CallbackQuery callbackQuery, Member member, UserState us) {
        String type = callbackQuery.getData();

        if (type.equals(CallbackType.DISH))
            sendDishMenu(callbackQuery, member, us);
        else if (type.equals(CallbackType.DISH + CallbackType.RANDOM))
            sendRandomDish(callbackQuery, member, us);
    }

    private void sendDishMenu(CallbackQuery callbackQuery, Member member, UserState userState) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        String text = "Выберите что показать \uD83D\uDC47"; // 👇
        EditMessageText em = new EditMessageText();
        em.setChatId(chatId);
        em.setMessageId(messageId);
        em.setText(text);
        em.setReplyMarkup(dishKeyboard.getChooseDishMenu(callbackQuery.getData()));

        execute(em);

        userState.setMessageId(messageId);
        userStateService.save(userState);
    }

    private void sendRandomDish(CallbackQuery callbackQuery, Member member, UserState userState) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        Dish dish = dishService.findRandom();

        String text = String.format("""
                Ваше случайное блюдо:
                *%s*
                
                Калорийность: *%s*
                
                ```Ингридиенты
                %s```
                
                ```Приготовление
                %s
                ```
                """, dish.getName(),
                dish.getCalories(),
                dish.getIngridients().stream()
                .map(i -> String.format(" • %s - %s %s", i.getName(), i.getAmount().stripTrailingZeros().toPlainString(), i.getUnit()))
                .collect(Collectors.joining("\n")),
                dish.getPreparation()
        );

        //delete prev message and send new with photo
        execute(DeleteMessage.builder().chatId(chatId).messageId(messageId).build());

        SendPhoto sm = new SendPhoto();
        sm.setChatId(chatId);
        sm.setCaption(text);
        sm.setParseMode("MarkdownV2");
        sm.setReplyMarkup(dishKeyboard.getRandomDish());
        sm.setPhoto(getPhoto(dish));

        Message message = execute(sm);
        userState.setMessageId(messageId);
    }


    @SneakyThrows
    private InputFile getPhoto(Dish dish) {
        File file = new File("/ZdorovoTochka/img/dish/" + dish.getPhoto());
        return new InputFile(file);
    }
}
