package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.newmsg.PoopRecordInitView;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

@Controller
public class PoopRecordInitController implements TelegramCommandController<Message, TelegramResponse> {

    private final PoopRecordService poopRecordService;

    public PoopRecordInitController(PoopRecordService poopRecordService,
            ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.POOPRECORD_INIT, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        var poopRecord = new PoopRecord(message);

        var poopRecordInputView = new PoopRecordInitView(poopRecord, poopRecordService);
        poopRecordInputView.setChatId(message.getChatId());

        var deleteMsg = new DeleteMessage();
        deleteMsg.setChatId(message.getChatId());
        deleteMsg.setMessageId(message.getMessageId());

        TelegramResponse response = new TelegramResponse();
        response.setSendMessage(poopRecordInputView);
        response.setDeleteMessage(deleteMsg);

        return response;
    }
}
