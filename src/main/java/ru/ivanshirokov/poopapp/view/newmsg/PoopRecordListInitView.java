package ru.ivanshirokov.poopapp.view.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.view.editmsg.PoopRecordListView;

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
