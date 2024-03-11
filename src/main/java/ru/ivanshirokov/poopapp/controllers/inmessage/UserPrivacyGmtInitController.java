package ru.ivanshirokov.poopapp.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.views.TelegramResponse;
import ru.ivanshirokov.poopapp.views.newmsg.UserPrivacyInitGmtView;

@Controller
public class UserPrivacyGmtInitController implements TelegramCommandController<Message, TelegramResponse> {

    public UserPrivacyGmtInitController(ApplicationContext context) {
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.USERPRIVACY_GMT_INIT, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        var userPrivacyInitGmtView = new UserPrivacyInitGmtView();
        userPrivacyInitGmtView.setChatId(message.getChatId());

        var deleteInitMsg = new DeleteMessage();
        deleteInitMsg.setChatId(message.getChatId());
        deleteInitMsg.setMessageId(message.getMessageId());

        var tgResponse = new TelegramResponse();
        tgResponse.addSendMessage(userPrivacyInitGmtView);
        tgResponse.addDeleteMessage(deleteInitMsg);

        return tgResponse;
    }
}
