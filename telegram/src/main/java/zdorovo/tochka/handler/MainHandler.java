package zdorovo.tochka.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import zdorovo.tochka.constant.BaseBlock;
import zdorovo.tochka.constant.CallbackType;
import zdorovo.tochka.entity.Member;
import zdorovo.tochka.entity.UserState;
import zdorovo.tochka.keyboard.MainKeyboard;
import zdorovo.tochka.service.MemberService;
import zdorovo.tochka.service.UserStateService;

@Component
public class MainHandler extends BaseHandler {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserStateService userStateService;
    @Autowired
    private MainKeyboard mainKeyboard;

    @Autowired
    private RegistrationHandler registrationHandler;

    public void handleUpdate(Update update) {
       if (update.getMessage() != null)
           handleMessage(update.getMessage());
       else if (update.getCallbackQuery() != null)
           handleCallback(update.getCallbackQuery());
    }

    public void handleMessage(Message message) {
        String text = message.getText();
        Long chatId = message.getChatId();

        Member member = memberService.findByChatId(chatId);
        //Generate userState if not exists
        UserState us = userStateService.getUserState(chatId);
        if (us == null) {
            us = UserState.builder()
                    .chatId(chatId)
                    .build();
            us = userStateService.save(us);
        }

        execute(DeleteMessage.builder().messageId(message.getMessageId()).chatId(message.getChatId()).build());

        //Register member if not exits
        if (member == null) {
            registrationHandler.handleMessage(message, member, us);
            return;
        }
        if (text != null && text.startsWith("/start")) {
            sendBaseMenu(message, member, us);
            return;
        }

        if (us.getBaseBlock() == BaseBlock.REGISTRATION)
            registrationHandler.handleMessage(message, member, us);
    }

    public void handleCallback(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();

        Member member = memberService.findByChatId(chatId);
        //Generate userState if not exists
        UserState us = userStateService.getUserState(chatId);
        if (us == null) {
            us = UserState.builder()
                    .chatId(chatId)
                    .build();
            us = userStateService.save(us);
        }

        if (data.equalsIgnoreCase(CallbackType.TO_MAIN_MENU))
            editBaseMenu(callbackQuery, member, us);
        else if (data.startsWith(CallbackType.REGISTER))
            registrationHandler.handleCallback(callbackQuery, member, us);
    }

    public void sendBaseMenu(Message message, Member member, UserState us) {
        String text = String.format("""
                <b>Добро пожалоть в ZдороVо и точка</b>
                Ваш текущий рост: %,.2f
                Ваш текущий вес: %,.2f
                """, member.getHeight(), member.getWeight());

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(text);
        sm.enableHtml(true);
        sm.setReplyMarkup(mainKeyboard.getBaseMenu());
        execute(sm);
    }
    public void editBaseMenu(CallbackQuery callbackQuery, Member member, UserState us) {
        String text = String.format("""
                <b>Добро пожалоть в ZдороVо и точка</b>
                Ваш текущий рост: %,.2f
                Ваш текущий вес: %,.2f
                """,    member.getHeight(), member.getWeight());

        EditMessageText em = new EditMessageText();
        em.setChatId(callbackQuery.getMessage().getChatId());
        em.setMessageId(callbackQuery.getMessage().getMessageId());
        em.setText(text);
        em.enableHtml(true);
        em.setReplyMarkup(mainKeyboard.getBaseMenu());
        execute(em);
    }

}
