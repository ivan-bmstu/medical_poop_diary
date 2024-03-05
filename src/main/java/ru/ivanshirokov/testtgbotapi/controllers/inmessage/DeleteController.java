package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.controllers.callback.CallbackControllerDispatcher;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

@Controller
public class DeleteController implements TelegramCommandController<CallbackQuery, TelegramResponse> {


    DeleteController(ApplicationContext context){
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.DELETE, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        var telegramResponse = new TelegramResponse();
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        deleteMessage.setChatId(callbackQuery.getMessage().getChatId());
        telegramResponse.setDeleteMessage(deleteMessage);
        return telegramResponse;
    }
}
