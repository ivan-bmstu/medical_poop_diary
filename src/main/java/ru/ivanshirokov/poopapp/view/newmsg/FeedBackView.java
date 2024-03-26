package ru.ivanshirokov.poopapp.view.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.view.Emoji;

import java.util.List;

public class FeedBackView extends SendMessage {

    private final String feedBackMsg;

    public FeedBackView(String feedbackMsg) {
        this.feedBackMsg = feedbackMsg;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText() {
        if (feedBackMsg.isBlank() || feedBackMsg.isEmpty()) {
            return """
                    Вы отправили feedback с пустым сообщением.
                    Не забудьте прикрепить свое обращение сразу после команды
                    '/feedback Ваше обращение'
                    """;
        }
        return "Ваше обращение записано, я обязательно ознакомлюсь с ним!\nПожалуйста, если вы хотите получить обратную связь, укажите это в тексте. Я пришлю вам ответ через бота\nВаше обращение:\n" + feedBackMsg;
    }

    private InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text("Закрыть " + Emoji.CANCEL)
                                .callbackData(TgPaths.DELETE)
                                .build()
                )).build();
    }
}
