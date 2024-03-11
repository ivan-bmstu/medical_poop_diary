package ru.ivanshirokov.poopapp.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ivanshirokov.poopapp.controllers.callback.CallbackControllerDispatcher;
import ru.ivanshirokov.poopapp.controllers.inmessage.InputMessageControllerDispatcher;
import ru.ivanshirokov.poopapp.views.TelegramResponse;


@Component("tgBot")
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;


    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class.getName());
    private final InputMessageControllerDispatcher inputMessageControllerDispatcher;
    private final CallbackControllerDispatcher callbackControllerDispatcher;

    public TelegramBot(
            @Value("${bot.token}") String botToken,
            InputMessageControllerDispatcher inputMessageControllerDispatcher,
            CallbackControllerDispatcher callbackControllerDispatcher) {
        super(botToken);
        this.inputMessageControllerDispatcher = inputMessageControllerDispatcher;
        this.callbackControllerDispatcher = callbackControllerDispatcher;
    }


    @Override
    public void onUpdateReceived(Update update) {
        TelegramResponse telegramResponse = new TelegramResponse();

        if (update.hasMessage()) {
            telegramResponse = inputMessageControllerDispatcher.getResponse(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            telegramResponse = callbackControllerDispatcher.getResponse(update.getCallbackQuery());
        }

        if (telegramResponse.hasDeleteMsg() || telegramResponse.hasEditMsg() || telegramResponse.hasSendMsg()) {

            try {
                for (EditMessageText editMessageText : telegramResponse.getEditMessagesText()) {
                    execute(editMessageText);
                }
            } catch (TelegramApiException e) { // TODO продумать про повторную отправку n-ое колличество раз и потом отправить сообщение пользователю что произошла ошибка :(
                logger.error("ERROR: can't process the request {}", update);
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            try {
                for (SendMessage sendMessage : telegramResponse.getSendMessages()) {
                    execute(sendMessage);
                }
            } catch (TelegramApiException e) {
                logger.error("ERROR: can't process the request {}", update);
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            try {
                for (DeleteMessage deleteMessage : telegramResponse.getDeleteMessages()) {
                    execute(deleteMessage);
                }
            } catch (TelegramApiException e) {
                logger.error("ERROR: can't process the request {}", update);
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}

