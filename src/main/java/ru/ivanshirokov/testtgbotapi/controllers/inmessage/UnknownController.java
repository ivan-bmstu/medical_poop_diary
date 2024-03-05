package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;
import ru.ivanshirokov.testtgbotapi.views.newmsg.UnknownView;

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
        telegramResponse.setSendMessage(unknownView);
        return telegramResponse;
    }
}
