package zdorovo.tochka.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import zdorovo.tochka.constant.BaseBlock;
import zdorovo.tochka.constant.CallbackType;
import zdorovo.tochka.constant.MenuStatus;
import zdorovo.tochka.entity.Member;
import zdorovo.tochka.entity.UserState;
import zdorovo.tochka.keyboard.RegistrationKeyboard;
import zdorovo.tochka.service.MemberService;
import zdorovo.tochka.service.UserStateService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
public class RegistrationHandler extends BaseHandler {

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserStateService userStateService;
    @Autowired
    private RegistrationKeyboard registrationKeyboard;

    public void handleMessage(Message message, Member member, UserState userState) {
        if (member == null) {
            log.error("Trying register existing user");
            return;
        }

        String mes = message.getText();
        if (mes.startsWith("/start")) {
            sendGreetings(message, userState, null);
            return;
        }

        if (userState.getStatus() == MenuStatus.WRITE_HEIGHT) {
            saveHeight(message, userState);
        } else if (userState.getStatus() == MenuStatus.WRITE_WEIGHT)
            saveWeight(message, userState);
    }
    public void handleCallback(CallbackQuery callbackQuery, Member member, UserState userState) {
        if (member == null) {
            log.error("Trying register existing user");
            return;
        }

        String type = callbackQuery.getData();

        if (type.equals(CallbackType.REGISTER))
            sendGreetings(callbackQuery.getMessage(), userState, userState.getMessageId());
        else if (type.equals(CallbackType.REGISTER + CallbackType.HEIGHT))
            askHeight(callbackQuery, userState);
        else if (type.equals(CallbackType.REGISTER + CallbackType.WEIGHT))
            askWeight(callbackQuery, userState);
        else if (type.equals(CallbackType.REGISTER + CallbackType.CONFIRM))
            registrateMember(callbackQuery, userState);
    }

    private void sendGreetings(Message message, UserState userState, Integer messageId) {
        String text = """
                <b>Вас приветствует ZдороVо и точка Бот!</b>
                Пожалуйста, заполните данные о себе.
                Вы сможете изменить их в любой момент.
                %s%s
                """;
        String heightText = "";
        if (userState.getHeight() != null) {
            heightText = "<i>Рост:</i> " + userState.getHeight().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "\n";
        }
        String weightText = "";
        if (userState.getWeight() != null) {
            weightText = "<i>Вес:</i> " + userState.getWeight().setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "\n";
        }
        text = String.format(text, heightText, weightText);

        if (messageId == null) {
            SendMessage sm = new SendMessage();
            sm.setChatId(message.getChatId());
            sm.setText(text);
            sm.enableHtml(true);
            sm.setReplyMarkup(registrationKeyboard.getRegistrationMenu(userState.getHeight(), userState.getWeight()));

            execute(sm);
        } else {
            EditMessageText em = new EditMessageText();
            em.setChatId(message.getChatId());
            em.setText(text);
            em.enableHtml(true);
            em.setReplyMarkup(registrationKeyboard.getRegistrationMenu(userState.getHeight(), userState.getWeight()));
            em.setMessageId(messageId);

            execute(em);
        }
    }

    private void askHeight(CallbackQuery callbackQuery, UserState userState) {
        String text = "Введите ваш рост";
        EditMessageText em = new EditMessageText();
        em.setChatId(callbackQuery.getMessage().getChatId());
        em.setText(text);
        em.setReplyMarkup(registrationKeyboard.getOnlyBack(callbackQuery.getData()));
        em.setMessageId(callbackQuery.getMessage().getMessageId());
        execute(em);

        userState.setBaseBlock(BaseBlock.REGISTRATION);
        userState.setStatus(MenuStatus.WRITE_HEIGHT);
        userState.setMessageId(callbackQuery.getMessage().getMessageId());
        userState.setCallbackData(callbackQuery.getData());
        userStateService.save(userState);
    }

    public void saveHeight(Message message, UserState userState) {
        String heightText = message.getText().replace(",", ".").replace("\\s", "");

        BigDecimal height;
        try {
            height = new BigDecimal(heightText).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception ignored) {
            String text = "Введите ваш рост\n\n⚠️ Пожалуйста, введите в формате 170 или 180.52";
            EditMessageText em = new EditMessageText();
            em.setChatId(message.getChatId());
            em.setText(text);
            em.setReplyMarkup(registrationKeyboard.getOnlyBack(userState.getCallbackData()));
            em.setMessageId(userState.getMessageId());
            execute(em);
            return;
        }

        userState.setHeight(height);
        userStateService.save(userState);

        sendGreetings(message, userState, userState.getMessageId());
    }

    private void askWeight(CallbackQuery callbackQuery, UserState userState) {
        String text = "Введите ваш вес";
        EditMessageText em = new EditMessageText();
        em.setChatId(callbackQuery.getMessage().getChatId());
        em.setText(text);
        em.setReplyMarkup(registrationKeyboard.getOnlyBack(callbackQuery.getData()));
        em.setMessageId(callbackQuery.getMessage().getMessageId());
        execute(em);

        userState.setBaseBlock(BaseBlock.REGISTRATION);
        userState.setStatus(MenuStatus.WRITE_WEIGHT);
        userState.setMessageId(callbackQuery.getMessage().getMessageId());
        userState.setCallbackData(callbackQuery.getData());
        userStateService.save(userState);
    }

    public void saveWeight(Message message, UserState userState) {
        String weightText = message.getText().replace(",", ".").replace("\\s", "");

        BigDecimal weight;
        try {
            weight = new BigDecimal(weightText).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception ignored) {
            String text = "Введите ваш вес\n\n⚠️ Пожалуйста, введите в формате 50.5 или 90.1    ";
            EditMessageText em = new EditMessageText();
            em.setChatId(message.getChatId());
            em.setText(text);
            em.setReplyMarkup(registrationKeyboard.getOnlyBack(userState.getCallbackData()));
            em.setMessageId(userState.getMessageId());
            execute(em);
            return;
        }

        userState.setWeight(weight);
        userStateService.save(userState);

        sendGreetings(message, userState, userState.getMessageId());
    }

    public void registrateMember(CallbackQuery callbackQuery, UserState userState) {
        Long chatId = callbackQuery.getMessage().getChatId();

        Member member = new Member();
        member.setHeight(userState.getHeight().setScale(4, RoundingMode.HALF_UP));
        member.setWeight(userState.getWeight().setScale(4, RoundingMode.HALF_UP));
        member.setChatId(chatId);
        member.setUsername(getUsername(callbackQuery));
        memberService.save(member);

        EditMessageText em = new EditMessageText();
        em.setText("\uD83D\uDD25 Поздравляем! \uD83D\uDD25\nВы зарегистрировались!");
        em.setMessageId(callbackQuery.getMessage().getMessageId());
        em.setChatId(chatId);
        em.setReplyMarkup(registrationKeyboard.returnToMenuButtonKeyboard());

        execute(em);
    }

    private String getUsername(CallbackQuery callbackQuery) {
        String name = callbackQuery.getFrom().getUserName();
        String randomUsername = "id" + RandomStringUtils.randomNumeric(6);
        //Valid username
        if(!(5 <= name.length() && name.length() <= 30)) {
            return randomUsername;
        }

        for(char c : name.toCharArray()) {
            if (!((int) '0' <= c && c <= (int) '9') && !((int) 'A' <= c && c <= (int) 'Z') && !((int) 'a' <= c && c <= (int) 'z')) {
                return randomUsername;
            }
        }

        Member usernameMember = memberService.findByUsername(name);
        if (usernameMember != null) {
            return randomUsername;
        }

        return name;
    }

}
