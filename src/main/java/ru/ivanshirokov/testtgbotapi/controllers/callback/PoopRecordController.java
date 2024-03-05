package ru.ivanshirokov.testtgbotapi.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.editmsg.PoopRecordView;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;


@Controller
public class PoopRecordController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;

    public PoopRecordController(PoopRecordService poopRecordService,
                                ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        poopRecordService.updateModelFromTgon(poopRecord, callbackQuery.getData());

        PoopRecordView poopRecordView = new PoopRecordView(poopRecord, poopRecordService);
        poopRecordView.setChatId(callbackQuery.getMessage().getChatId());
        poopRecordView.setMessageId(callbackQuery.getMessage().getMessageId());

        var callbackResponse = new TelegramResponse();
        callbackResponse.setEditMessageText(poopRecordView);

        return callbackResponse;
    }

}
