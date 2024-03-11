package ru.ivanshirokov.poopapp.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.models.UserPrivacy;
import ru.ivanshirokov.poopapp.services.UserPrivacyService;
import ru.ivanshirokov.poopapp.views.newmsg.StartView;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

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
