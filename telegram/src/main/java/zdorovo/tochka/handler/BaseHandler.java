package zdorovo.tochka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import zdorovo.tochka.bot.TelegramBot;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Slf4j
public class BaseHandler {

    @Autowired
    private TelegramBot telegramBot;

    public Message sendExceptionMessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format("<strong>%s</strong>",text));
        sendMessage.enableHtml(true);
        try{
            return telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return  null;
    }
    public void sendWarningMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format("<strong>%s</strong>",text));
        sendMessage.enableHtml(true);
        try{
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendWarningMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format("<strong>%s</strong>",text));
        sendMessage.enableHtml(true);
        sendMessage.setReplyMarkup(keyboard);
        try{
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendWarningMessage(Long chatId, int messageId, String text) {
        EditMessageText em = new EditMessageText();
        em.setChatId(chatId.toString());
        em.setText(String.format("<strong>%s</strong>",text));
        em.enableHtml(true);
        em.setMessageId(messageId);
        try{
            telegramBot.execute(em);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) {
        try{
            return telegramBot.execute(method);
        } catch (TelegramApiException e) {
            log.info("execute error = {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Message execute(SendVideo sendVideo) {
        try{
            return telegramBot.execute(sendVideo);
        } catch (TelegramApiException e) {
            log.info("execute error = {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Message execute(SendPhoto sendPhoto) {
        try{
            return telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Message execute(SendDocument sendDocument) {
        try{
            return telegramBot.execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Message> execute(SendMediaGroup sendMediaGroup) {
        try{
            return telegramBot.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public org.telegram.telegrambots.meta.api.objects.File execute(GetFile getFile) {
        try {
            return telegramBot.execute(getFile);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public File downloadFileAsStream(String filePath, File outputFile) {
        try {
            return telegramBot.downloadFile(filePath, outputFile);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatType(String type) {
        return type.replace(";","");
    }

}
