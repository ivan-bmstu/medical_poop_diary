package ru.ivanshirokov.testtgbotapi.views;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;

import java.util.List;

public class PoopRecordSaveView extends EditMessageText {

    private final long countRecordsForUser;

    public PoopRecordSaveView(long countRecordsForUser) {
        this.countRecordsForUser = countRecordsForUser;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText(){
        return "Запись успешна произведена! Всего сделано записей: " + countRecordsForUser;
    }

    private InlineKeyboardMarkup getKeyboard(){
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text("Убрать сообщение")
                                .callbackData(TgPaths.DELETE)
                                .build()
                )).build();
    }
}
