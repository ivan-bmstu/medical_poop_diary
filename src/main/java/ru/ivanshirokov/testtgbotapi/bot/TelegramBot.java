package ru.ivanshirokov.testtgbotapi.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ivanshirokov.testtgbotapi.controllers.callback.CallbackControllerDispatcher;
import ru.ivanshirokov.testtgbotapi.controllers.inmessage.InputMessageControllerDispatcher;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;


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
    public void onUpdateReceived(Update update) {//TODO возможно ли переделать на единый интерфейс TgResponse?
        TelegramResponse telegramResponse = new TelegramResponse();

        if (update.hasMessage()) {
            telegramResponse = inputMessageControllerDispatcher.getResponse(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            telegramResponse = callbackControllerDispatcher.getResponse(update.getCallbackQuery()); //TODO можно сразу исправлять, удалять и отправлять несколько сообщений, надо возвращать List
        }

        if (telegramResponse.hasDeleteMsg() || telegramResponse.hasEditMsg() || telegramResponse.hasSendMsg()) {
            if (telegramResponse.hasEditMsg()) {
                try {
                    execute(telegramResponse.getEditMessageText());
                } catch (TelegramApiException e) {
                    logger.error("ERROR: can't process the request {}", update);
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            if (telegramResponse.hasSendMsg()) {
                try {
                    execute(telegramResponse.getSendMessage());
                } catch (TelegramApiException e) {
                    logger.error("ERROR: can't process the request {}", update);
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            if (telegramResponse.hasDeleteMsg()) {
                try {
                    execute(telegramResponse.getDeleteMessage());
                } catch (TelegramApiException e) {
                    logger.error("ERROR: can't process the request {}", update);
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}

