package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.service.AdminUserService;
import ru.ivanshirokov.poopapp.service.UserPrivacyService;
import ru.ivanshirokov.poopapp.view.TelegramResponse;
import ru.ivanshirokov.poopapp.view.newmsg.FeedBackView;

import java.util.List;

@Controller
public class FeedBackController implements TelegramCommandController<Message, TelegramResponse> {

    private final AdminUserService aus;

    public FeedBackController(AdminUserService aus, ApplicationContext context) {
        this.aus = aus;
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.FEEDBACK, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        String msgText = message.getText();

        var tgResponse = new TelegramResponse();

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());
        tgResponse.addDeleteMessage(deleteMessage);

        String text = msgText.substring(9);
        if (!text.isBlank()&&!text.isEmpty()) {
            var admins = aus.getAdmins();
            for (var admin : admins) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Получен feedback от пользователя с chat_id:'"
                        + message.getChatId() + "'\nТекст сообщения:\n" + text);
                sendMessage.setChatId(admin.getChatId());
                tgResponse.addSendMessage(sendMessage);
            }
        }

        var feedbackView = new FeedBackView(text);
        feedbackView.setChatId(message.getChatId());
        tgResponse.addSendMessage(feedbackView);

        return tgResponse;
    }
}
