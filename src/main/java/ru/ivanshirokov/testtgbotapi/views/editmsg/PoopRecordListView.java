package ru.ivanshirokov.testtgbotapi.views.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.Emoji;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PoopRecordListView extends EditMessageText {

    private final List<PoopRecord> poopRecords;
    private final PoopRecordService poopRecordService;
    private final int page;
    public PoopRecordListView(List<PoopRecord> poopRecords,
                              PoopRecordService poopRecordService,
                              int page) {
        this.poopRecordService = poopRecordService;
        this.poopRecords = poopRecords;
        this.page = page;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText(){
        String msgText = "";
        for (int i = 1; i < poopRecords.size(); i ++){
            msgText = msgText + printPoopRecord(poopRecords.get(i), i);
        }
        return msgText;
    }

    private InlineKeyboardMarkup getKeyboard(){
        //TODO спешил переформатировать
        InlineKeyboardButton btnForward = null;
        if(poopRecords.size() == 5){
            String forwardBtnCallbackData = TgPaths.POOPRECORD_LIST + "_" + (page + 1);
            String forward = "Вперед ";
            btnForward = InlineKeyboardButton.builder()
                    .text(forward)
                    .callbackData(forwardBtnCallbackData)
                    .build();
        }

        InlineKeyboardButton btnBack = null;
        if (page > 0){
            String forwardBtnCallbackData = TgPaths.POOPRECORD_LIST + "_" + (page - 1);
            String forward = "Назад ";
            btnBack = InlineKeyboardButton.builder()
                    .text(forward)
                    .callbackData(forwardBtnCallbackData)
                    .build();
        }
        List<InlineKeyboardButton> row = new ArrayList<>();
        if(btnForward != null){row.add(btnForward);}
        if(btnBack != null){row.add(btnBack);}
        row.add(InlineKeyboardButton.builder()
                .text("Закрыть " + Emoji.CANCEL)
                .callbackData(TgPaths.DELETE)
                .build());


        var keyboard = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();
        return keyboard;
    }

    private String printPoopRecord(PoopRecord poopRecord, int i){
        //TODO тут спешил, топорно написал, надо переделать
        String border = "==================================\n";
        String recordNumber = "|" + (i + page * 4) + "|";
        String blood = "" + Emoji.BLOOD.repeat(howMuch(poopRecord.getBlood()));
        String mucus = "" + Emoji.MUCUS.repeat(howMuch(poopRecord.getMucus()));
        String stool = " стул:";

        LocalDateTime ldt = poopRecord.getDateTime().plusHours(3);
        String date = " <=> " + ldt.format(poopRecordService.printFormatter) + "\n";

        int stoolVal = poopRecord.getStool();

        if(poopRecord.getBlood()<0){
            blood = "";
        }
        if(poopRecord.getMucus()<0){
            mucus = "";
        }
        if (poopRecord.getStool()<0){
            stool = "";
        }

        if (stoolVal<0){
            stool = "";
        }
        if(stoolVal == 0){
            stool = stool + " диарея";
        }
        if(stoolVal > 0 && stoolVal < 5){
            stool = stool + " мягкий";
        }
        if(stoolVal == 5) {
            stool = stool + " нормальный";
        }
        if(stoolVal>5){
            stool = stool + " твердый";
        }
        return border + recordNumber + blood + mucus + stool + date + border;
    }

    private int howMuch(int i){
        if(i <= 0){
            return 0;
        }
        if (i < 5) {
            return 1;
        }
        if (i < 8){
            return 2;
        }
        return 3;
    }
}
