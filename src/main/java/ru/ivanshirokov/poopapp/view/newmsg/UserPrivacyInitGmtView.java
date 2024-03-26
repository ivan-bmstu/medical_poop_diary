package ru.ivanshirokov.poopapp.view.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.view.Emoji;

import java.util.ArrayList;
import java.util.List;

public class UserPrivacyInitGmtView extends SendMessage {

    public UserPrivacyInitGmtView() {
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText(){
        return "Укажите ваш часовой пояс";
    }

    private InlineKeyboardMarkup getKeyboard(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i <= 11; i++) {
            row.add(InlineKeyboardButton.builder()
                    .text("GMT+" + i)
                    .callbackData(TgPaths.USERPRIVACY_GMT + "_" + i)
                    .build());

        }
        return InlineKeyboardMarkup.builder()
                .keyboardRow(row.subList(0,3))
                .keyboardRow(row.subList(3,6))
                .keyboardRow(row.subList(6,9))
                .keyboardRow(row.subList(9,12))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text("Отмена "+ Emoji.CANCEL)
                                .callbackData(TgPaths.DELETE)
                                .build()
                ))
                .build();

    }
}
