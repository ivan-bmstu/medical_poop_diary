package ru.ivanshirokov.testtgbotapi.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.editmsg.PoopRecordInputView;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

@Controller
public class PoopRecordInputController implements TelegramCommandController<CallbackQuery, TelegramResponse> {


    private final PoopRecordService poopRecordService;

    public PoopRecordInputController(PoopRecordService poopRecordService,
                                     ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD_INPUT, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        poopRecordService.updateModelFromTgon(poopRecord, callbackQuery.getData());

        PoopRecordInputView poopRecordInputView = new PoopRecordInputView(poopRecord, poopRecordService);
        poopRecordInputView.setChatId(callbackQuery.getMessage().getChatId());
        poopRecordInputView.setMessageId(callbackQuery.getMessage().getMessageId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.setEditMessageText(poopRecordInputView);
        return telegramResponse;
    }
}
