package ru.ivanshirokov.poopapp.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.services.PoopRecordService;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;
import ru.ivanshirokov.poopapp.views.editmsg.PoopRecordSaveView;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

@Controller
public class PoopRecordSaveController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;
    private final PoopRecordTgonMapper poopRecordTgonMapper;

    public PoopRecordSaveController(PoopRecordService poopRecordService,
                                    ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        poopRecordTgonMapper = new PoopRecordTgonMapper();
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD_SAVE, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        poopRecordTgonMapper.updateModelFromTgon(poopRecord, callbackQuery.getData());

        poopRecordService.save(poopRecord);

        long countRecordsForUser = poopRecordService.getCountByUser(poopRecord.getUserPrivacy());
        var editView = new PoopRecordSaveView(countRecordsForUser);
        editView.setChatId(callbackQuery.getMessage().getChatId());
        editView.setMessageId(callbackQuery.getMessage().getMessageId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.addEditMessageText(editView);

        return telegramResponse;
    }
}
