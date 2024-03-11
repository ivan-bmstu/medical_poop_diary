package ru.ivanshirokov.poopapp.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.services.PoopRecordService;
import ru.ivanshirokov.poopapp.services.UserPrivacyService;
import ru.ivanshirokov.poopapp.views.newmsg.PoopRecordInitView;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

@Controller
public class PoopRecordInitController implements TelegramCommandController<Message, TelegramResponse> {

    private final PoopRecordService poopRecordService;
    private final UserPrivacyService userPrivacyService;

    public PoopRecordInitController(PoopRecordService poopRecordService,
            UserPrivacyService userPrivacyService,
            ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        this.userPrivacyService = userPrivacyService;
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.POOPRECORD_INIT, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        var poopRecord = new PoopRecord(message);
        userPrivacyService.readLanguageGmtFromDB(poopRecord.getUserPrivacy());

        var poopRecordInputView = new PoopRecordInitView(poopRecord, poopRecordService);
        poopRecordInputView.setChatId(message.getChatId());

        var deleteMsg = new DeleteMessage();
        deleteMsg.setChatId(message.getChatId());
        deleteMsg.setMessageId(message.getMessageId());

        TelegramResponse response = new TelegramResponse();
        response.addSendMessage(poopRecordInputView);
        response.addDeleteMessage(deleteMsg);

        return response;
    }
}
