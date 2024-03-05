package ru.ivanshirokov.testtgbotapi.views.newmsg;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class UnknownView extends SendMessage {

    public UnknownView() {
        String msgText = """
                К сожалению, я не знаю такой команды 😔
                Для выбора нужной команды, воспользуйтесь, пожалуйста, кнопками на нижней панели
                Если вы хотели бы сообщить об ошибке, или у вас есть интересное предложение, напишите пожалуйста команду /feedback и оставьте сообщение
                """;
        setText(msgText);
    }
}
