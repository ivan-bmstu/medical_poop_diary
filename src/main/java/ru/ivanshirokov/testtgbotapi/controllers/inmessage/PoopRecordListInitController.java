package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;
import ru.ivanshirokov.testtgbotapi.views.newmsg.PoopRecordListInitView;

import java.util.List;

@Controller
public class PoopRecordListInitController implements TelegramCommandController<Message, TelegramResponse> {

    private final PoopRecordService poopRecordService;

    public PoopRecordListInitController(PoopRecordService poopRecordService,
                                        ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(InputMessageControllerDispatcher.class).addPath(TgPaths.POOPRECORD_LIST_INIT, this);
    }

    @Override
    public TelegramResponse getResponse(Message message) {
        List<PoopRecord> poopRecords = poopRecordService.getPoopRecords(message.getFrom().getId(), 0);

        var view = new PoopRecordListInitView(poopRecords, poopRecordService, 0);
        view.setChatId(message.getChatId());


        var deleteMsg = new DeleteMessage();
        deleteMsg.setChatId(message.getChatId());
        deleteMsg.setMessageId(message.getMessageId());

        TelegramResponse response = new TelegramResponse();
        response.setSendMessage(view);
        response.setDeleteMessage(deleteMsg);

        return response;
    }
}
