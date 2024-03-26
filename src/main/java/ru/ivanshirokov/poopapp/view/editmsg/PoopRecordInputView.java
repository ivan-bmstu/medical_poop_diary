package ru.ivanshirokov.poopapp.view.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;
import ru.ivanshirokov.poopapp.view.Emoji;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PoopRecordInputView extends EditMessageText {

    private final PoopRecord poopRecord;
    private final PoopRecordTgonMapper poopRecordTgonMapper;
    public PoopRecordInputView(PoopRecord poopRecord) {
        this.poopRecordTgonMapper = new PoopRecordTgonMapper();
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

        Function<Integer, String> callbackDataWhichParNeedSet = i ->
                TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewBlood(poopRecord, i);

        if(poopRecord.getMucus() < -1) {
            callbackDataWhichParNeedSet = i -> TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewMucus(poopRecord, i);
        }

        if (poopRecord.getStool() < -1) {
            callbackDataWhichParNeedSet = i -> TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewStool(poopRecord, i);
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
                .callbackData(TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgon(poopRecord))
                .build();
        row3.add(btnBack);

        var keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(row1, row2, row3));
        return keyboard;
    }

}
