package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.view.TelegramResponse;
import ru.ivanshirokov.poopapp.view.newmsg.UnknownView;

@Controller
public class UnknownController implements TelegramCommandController<Message, TelegramResponse> {


    public UnknownController(ApplicationContext context) {
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.UNKNOWN, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        var unknownView = new UnknownView();
        unknownView.setChatId(message.getChatId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.addSendMessage(unknownView);
        return telegramResponse;
    }
}
