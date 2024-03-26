package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.UserPrivacy;
import ru.ivanshirokov.poopapp.service.UserPrivacyService;
import ru.ivanshirokov.poopapp.view.newmsg.StartView;
import ru.ivanshirokov.poopapp.view.TelegramResponse;

@Controller
public class StartController implements TelegramCommandController<Message, TelegramResponse> {

    private final UserPrivacyService userPrivacyService;

    public StartController(UserPrivacyService userPrivacyService,
            ApplicationContext context) {
        this.userPrivacyService = userPrivacyService;
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.START, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        var userPrivacy = new UserPrivacy(message);
        if (!userPrivacyService.isPresent(userPrivacy)){
            userPrivacyService.save(userPrivacy);
        }

        var startView = new StartView();
        startView.setChatId(message.getChatId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.addSendMessage(startView);
        return telegramResponse;
    }
}
