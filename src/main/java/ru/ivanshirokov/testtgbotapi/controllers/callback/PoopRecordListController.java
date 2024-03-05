package ru.ivanshirokov.testtgbotapi.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;
import ru.ivanshirokov.testtgbotapi.views.editmsg.PoopRecordListView;

import java.util.List;

@Controller
public class PoopRecordListController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final PoopRecordService poopRecordService;

    public PoopRecordListController(PoopRecordService poopRecordService,
                                ApplicationContext context) {
        this.poopRecordService = poopRecordService;
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.POOPRECORD_LIST, this);
    }

    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        String[] input = callbackQuery.getData().split("_");
        int page = Integer.parseInt(input[input.length-1]);

        List<PoopRecord> poopRecords = poopRecordService.getPoopRecords(callbackQuery.getFrom().getId(), page);

        var view = new PoopRecordListView(poopRecords, poopRecordService, page);
        view.setChatId(callbackQuery.getMessage().getChatId());
        view.setMessageId(callbackQuery.getMessage().getMessageId());

        TelegramResponse response = new TelegramResponse();
        response.setEditMessageText(view);
        return response;
    }
}
