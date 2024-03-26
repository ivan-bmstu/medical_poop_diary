package ru.ivanshirokov.poopapp.view.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.ivanshirokov.poopapp.controller.TgPaths;

import java.util.List;

public class StartView extends SendMessage {

    public StartView() {
        String messageText = """
                Добро пожаловать в бот Белый круглый друг!
                Моя цель - помочь тебе вести дневник посещения туалета.
                Надеюсь, наш с тобой дневник поможет тебе скорее выздороветь!🙂
                Обрати внимание, что по-умолчанию, используется московское время. Часовой пояс можно изменить в нижнем меню
                """;
        setText(messageText);
        ReplyKeyboardMarkup keyboard = ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(
                        KeyboardButton.builder()
                                .text(TgPaths.POOPRECORD_INIT)
                                .build())))
                .keyboardRow(new KeyboardRow(List.of(
                        KeyboardButton.builder()
                                .text(TgPaths.POOPRECORD_LIST_INIT)
                                .build())))
                .keyboardRow(new KeyboardRow(List.of(
                        KeyboardButton.builder()
                                .text(TgPaths.USERPRIVACY_GMT_INIT)
                                .build())))
                .build();
        keyboard.setResizeKeyboard(true);
        setReplyMarkup(keyboard);
    }
}
