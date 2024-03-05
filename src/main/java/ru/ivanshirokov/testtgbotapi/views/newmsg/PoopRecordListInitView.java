package ru.ivanshirokov.testtgbotapi.views.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.editmsg.PoopRecordListView;

import java.util.List;

public class PoopRecordListInitView extends SendMessage {

    public PoopRecordListInitView(List<PoopRecord> poopRecords,
                                  PoopRecordService poopRecordService,
                                  int page){
        PoopRecordListView poopRecordListView = new PoopRecordListView(poopRecords, poopRecordService, page);
        setText(poopRecordListView.getText());
        setReplyMarkup(poopRecordListView.getReplyMarkup());
    }
}
