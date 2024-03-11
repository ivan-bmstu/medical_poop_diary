package ru.ivanshirokov.poopapp.views.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.services.PoopRecordService;
import ru.ivanshirokov.poopapp.views.editmsg.PoopRecordListView;

import java.util.List;

public class PoopRecordListInitView extends SendMessage {

    public PoopRecordListInitView(List<PoopRecord> poopRecords,
                                  PoopRecordService poopRecordService,
                                  int page){
        PoopRecordListView poopRecordListView = new PoopRecordListView(poopRecords, page);
        setText(poopRecordListView.getText());
        setReplyMarkup(poopRecordListView.getReplyMarkup());
    }
}
