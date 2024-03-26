package ru.ivanshirokov.poopapp.view.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.service.PoopRecordService;
import ru.ivanshirokov.poopapp.view.editmsg.PoopRecordView;

//need SendMessage
public class PoopRecordInitView extends SendMessage {

    public PoopRecordInitView(PoopRecord poopRecord,
                              PoopRecordService poopRecordService) {
        PoopRecordView view = new PoopRecordView(poopRecord);
        setText(view.getText());
        setReplyMarkup(view.getReplyMarkup());
    }
}
