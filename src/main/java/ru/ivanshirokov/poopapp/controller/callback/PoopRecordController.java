package ru.ivanshirokov.poopapp.controller.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.service.UserPrivacyService;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;
import ru.ivanshirokov.poopapp.view.editmsg.PoopRecordView;
import ru.ivanshirokov.poopapp.view.TelegramResponse;


@Controller
public class PoopRecordController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;
    private final UserPrivacyService userPrivacyService;
    private final PoopRecordTgonMapper poopRecordTgonMapper;

    public PoopRecordController(PoopRecordService poopRecordService,
                                UserPrivacyService userPrivacyService,
                                ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        this.userPrivacyService = userPrivacyService;
        poopRecordTgonMapper = new PoopRecordTgonMapper();
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        userPrivacyService.readLanguageGmtFromDB(poopRecord.getUserPrivacy());
        poopRecordTgonMapper.updateModelFromTgon(poopRecord, callbackQuery.getData());

        PoopRecordView poopRecordView = new PoopRecordView(poopRecord);
        poopRecordView.setChatId(callbackQuery.getMessage().getChatId());
        poopRecordView.setMessageId(callbackQuery.getMessage().getMessageId());

        TelegramResponse callbackResponse = new TelegramResponse();
        callbackResponse.addEditMessageText(poopRecordView);

        return callbackResponse;
    }

}
