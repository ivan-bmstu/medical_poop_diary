package ru.ivanshirokov.poopapp.controller.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.exception.InvalidCallbackQueryData;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.view.TelegramResponse;
import ru.ivanshirokov.poopapp.view.editmsg.PoopRecordListView;

import java.util.Arrays;
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
        String input = Arrays.stream(callbackQuery.getData().split("_"))
                .filter(s -> !s.contains("/"))
                .findFirst().orElseThrow(InvalidCallbackQueryData::new);
        int page = Integer.parseInt(input);

        List<PoopRecord> poopRecords = poopRecordService.getPoopRecords(callbackQuery.getFrom().getId(), page);

        var view = new PoopRecordListView(poopRecords, page);
        view.setChatId(callbackQuery.getMessage().getChatId());
        view.setMessageId(callbackQuery.getMessage().getMessageId());

        TelegramResponse response = new TelegramResponse();
        response.addEditMessageText(view);
        return response;
    }
}
