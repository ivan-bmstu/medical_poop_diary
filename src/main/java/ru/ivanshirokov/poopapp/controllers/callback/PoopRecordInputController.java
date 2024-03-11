package ru.ivanshirokov.poopapp.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.services.PoopRecordService;
import ru.ivanshirokov.poopapp.services.UserPrivacyService;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;
import ru.ivanshirokov.poopapp.views.editmsg.PoopRecordInputView;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

@Controller
public class PoopRecordInputController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;
    private final UserPrivacyService userPrivacyService;
    private final PoopRecordTgonMapper poopRecordTgonMapper;

    public PoopRecordInputController(PoopRecordService poopRecordService,
                                     UserPrivacyService userPrivacyService,
                                     ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        this.userPrivacyService = userPrivacyService;
        poopRecordTgonMapper = new PoopRecordTgonMapper();
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD_INPUT, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        PoopRecord poopRecord = new PoopRecord(callbackQuery);
        userPrivacyService.readLanguageGmtFromDB(poopRecord.getUserPrivacy());
        poopRecordTgonMapper.updateModelFromTgon(poopRecord, callbackQuery.getData());

        PoopRecordInputView poopRecordInputView = new PoopRecordInputView(poopRecord);
        poopRecordInputView.setChatId(callbackQuery.getMessage().getChatId());
        poopRecordInputView.setMessageId(callbackQuery.getMessage().getMessageId());

        var telegramResponse = new TelegramResponse();
        telegramResponse.addEditMessageText(poopRecordInputView);

        return telegramResponse;
    }
}
