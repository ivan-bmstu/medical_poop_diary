package ru.ivanshirokov.testtgbotapi.views.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.editmsg.PoopRecordView;

//need SendMessage
public class PoopRecordInitView extends SendMessage {

    public PoopRecordInitView(PoopRecord poopRecord,
                              PoopRecordService poopRecordService) {
        PoopRecordView view = new PoopRecordView(poopRecord, poopRecordService);
        setText(view.getText());
        setReplyMarkup(view.getReplyMarkup());
    }
}
