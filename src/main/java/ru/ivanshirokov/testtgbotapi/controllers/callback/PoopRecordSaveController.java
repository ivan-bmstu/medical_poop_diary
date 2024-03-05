package ru.ivanshirokov.testtgbotapi.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.PoopRecordSaveView;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

@Controller
public class PoopRecordSaveController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;

    public PoopRecordSaveController(PoopRecordService poopRecordService,
                                    ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD_SAVE, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        poopRecordService.updateModelFromTgon(poopRecord, callbackQuery.getData());

        poopRecordService.save(poopRecord);

        long countRecordsForUser = poopRecordService.getCountByUser(poopRecord.getUserPrivacy());
        var editView = new PoopRecordSaveView(countRecordsForUser);
        editView.setChatId(callbackQuery.getMessage().getChatId());
        editView.setMessageId(callbackQuery.getMessage().getMessageId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.setEditMessageText(editView);

        return telegramResponse;
    }
}
