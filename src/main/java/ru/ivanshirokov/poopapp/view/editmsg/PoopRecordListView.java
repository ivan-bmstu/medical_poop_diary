package ru.ivanshirokov.poopapp.view.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.util.UtilDateTimeFormatter;
import ru.ivanshirokov.poopapp.view.Emoji;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class PoopRecordListView extends EditMessageText {

    private final List<PoopRecord> poopRecords;
    private final int page;
    public PoopRecordListView(List<PoopRecord> poopRecords,
                              int page) {
        this.poopRecords = poopRecords;
        this.page = page;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText(){
        String msgText = "Ваши записи:\n";
        for (int i = 0; i < poopRecords.size(); i ++){
            msgText = msgText + printPoopRecord(poopRecords.get(i), (i+1));
        }
        return msgText;
    }

    private InlineKeyboardMarkup getKeyboard(){

        List<InlineKeyboardButton> row = new ArrayList<>();


        if(poopRecords.size() == 5){ //TODO переделать чтобы не возвращать пустую страницу если дальше записей нет
            String forwardBtnCallbackData = TgPaths.POOPRECORD_LIST + "_" + (page + 1);
            String forward = "Вперед ";
            row.add(InlineKeyboardButton.builder()
                    .text(forward)
                    .callbackData(forwardBtnCallbackData)
                    .build());
        }

        if (page > 0){
            String forwardBtnCallbackData = TgPaths.POOPRECORD_LIST + "_" + (page - 1);
            String forward = "Назад ";
            row.add(InlineKeyboardButton.builder()
                    .text(forward)
                    .callbackData(forwardBtnCallbackData)
                    .build());
        }

        List<InlineKeyboardButton> lastRow = new ArrayList<>();
        lastRow.add(InlineKeyboardButton.builder()
                .text("Закрыть " + Emoji.CANCEL)
                .callbackData(TgPaths.DELETE)
                .build());


        return InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .keyboardRow(lastRow)
                .build();
    }

    private String printPoopRecord(PoopRecord poopRecord, int i){
        String border = "=================================\n";
        int recordNumber = i + page * 5;

        LocalDateTime ldt = poopRecord.getDateTime().plusHours(poopRecord.getUserPrivacy().getGmt());
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("GMT+" + poopRecord.getUserPrivacy().getGmt()));
        String date = zdt.format(UtilDateTimeFormatter.printFormatter);

        String blood = Emoji.BLOOD + ": [N/A]";
        if(poopRecord.getBlood() >= 0){
            blood = Emoji.BLOOD + ": " + Emoji.numeralEmoji.get(poopRecord.getBlood());
        }

        String mucus = Emoji.MUCUS + ": [N/A]";
        if(poopRecord.getMucus() >= 0){
            mucus = Emoji.MUCUS + ": " + Emoji.numeralEmoji.get(poopRecord.getMucus());
        }

        String stool = Emoji.SOLID + ": [N/A]";
        if (poopRecord.getStool() >= 0){
            stool = Emoji.SOLID + ": " + Emoji.numeralEmoji.get(poopRecord.getStool());;
        }

        return border
                + "|" + recordNumber + "|"
                + blood + "    "
                + mucus + "    "
                + stool + "\n"
                + date + "\n"
                + border + "\n";
    }
}
