package ru.ivanshirokov.poopapp.views.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.services.PoopRecordService;
import ru.ivanshirokov.poopapp.views.editmsg.PoopRecordView;

//need SendMessage
public class PoopRecordInitView extends SendMessage {

    public PoopRecordInitView(PoopRecord poopRecord,
                              PoopRecordService poopRecordService) {
        PoopRecordView view = new PoopRecordView(poopRecord);
        setText(view.getText());
        setReplyMarkup(view.getReplyMarkup());
    }
}
