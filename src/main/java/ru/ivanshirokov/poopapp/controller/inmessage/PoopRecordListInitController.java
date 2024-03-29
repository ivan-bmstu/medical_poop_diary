package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.view.TelegramResponse;
import ru.ivanshirokov.poopapp.view.newmsg.PoopRecordListInitView;

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
        response.addSendMessage(view);
        response.addDeleteMessage(deleteMsg);

        return response;
    }
}
