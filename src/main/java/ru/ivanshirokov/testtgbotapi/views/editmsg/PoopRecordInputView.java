package ru.ivanshirokov.testtgbotapi.views.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.Emoji;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PoopRecordInputView extends EditMessageText {

    private final PoopRecord poopRecord;
    private final PoopRecordService poopRecordService;
    public PoopRecordInputView(PoopRecord poopRecord,
                               PoopRecordService poopRecordService) {
        this.poopRecordService = poopRecordService;
        this.poopRecord = poopRecord;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText(){
        String emoji = Emoji.BLOOD;
        if (poopRecord.getMucus() < -1) {
            emoji = Emoji.MUCUS;
        } else if (poopRecord.getStool() < -1) {
            emoji = Emoji.SOLID;
        }
        return "Установите значение для показателя: " + emoji;
    }

    private InlineKeyboardMarkup getKeyboard(){

        Function<Integer, String> callbackDataWhichParNeedSet = i -> TgPaths.POOPRECORD + poopRecordService.mapToTgonNewBlood(poopRecord, i);

        if(poopRecord.getMucus() < -1) {
            callbackDataWhichParNeedSet = i -> TgPaths.POOPRECORD + poopRecordService.mapToTgonNewMucus(poopRecord, i);
        }

        if (poopRecord.getStool() < -1) {
            callbackDataWhichParNeedSet = i -> TgPaths.POOPRECORD + poopRecordService.mapToTgonNewStool(poopRecord, i);
        }

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(Emoji.numeralEmoji.get(i));
            btn.setCallbackData(callbackDataWhichParNeedSet.apply(i));
            row1.add(btn);
        }

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        for (int i = 5; i < 10; i++){
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(Emoji.numeralEmoji.get(i));
            btn.setCallbackData(callbackDataWhichParNeedSet.apply(i));
            row2.add(btn);
        }

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        var btnBack = InlineKeyboardButton.builder()
                .text("Вернуться назад " + Emoji.BACK)
                .callbackData(TgPaths.POOPRECORD + poopRecordService.mapToTgon(poopRecord))
                .build();
        row3.add(btnBack);

        var keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row1, row2, row3));
        return keyboard;
    }

}
