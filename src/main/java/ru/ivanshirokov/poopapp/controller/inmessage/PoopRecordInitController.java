package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.service.UserPrivacyService;
import ru.ivanshirokov.poopapp.view.newmsg.PoopRecordInitView;
import ru.ivanshirokov.poopapp.view.TelegramResponse;

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
