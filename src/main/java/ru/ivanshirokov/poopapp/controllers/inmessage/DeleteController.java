package ru.ivanshirokov.poopapp.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.controllers.callback.CallbackControllerDispatcher;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

@Controller
public class DeleteController implements TelegramCommandController<CallbackQuery, TelegramResponse> {


    DeleteController(ApplicationContext context){
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.DELETE, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        deleteMessage.setChatId(callbackQuery.getMessage().getChatId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.addDeleteMessage(deleteMessage);
        return telegramResponse;
    }
}
