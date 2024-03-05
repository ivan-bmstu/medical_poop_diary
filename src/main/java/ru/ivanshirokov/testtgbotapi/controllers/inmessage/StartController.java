package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.UserPrivacy;
import ru.ivanshirokov.testtgbotapi.services.UserPrivacyService;
import ru.ivanshirokov.testtgbotapi.views.newmsg.StartView;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

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
        telegramResponse.setSendMessage(startView);
        return telegramResponse;
    }
}
