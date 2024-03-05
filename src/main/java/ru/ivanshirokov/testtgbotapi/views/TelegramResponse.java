package ru.ivanshirokov.testtgbotapi.views;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

//TODO подумать про sendmessage из ответа на CallBack - крутится долго нажатая кнопка, а обновить не получается
//TODO а может его лучше в пакет controller?
public class TelegramResponse {

    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private DeleteMessage deleteMessage;

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public EditMessageText getEditMessageText() {
        return editMessageText;
    }

    public void setEditMessageText(EditMessageText editMessageText) {
        this.editMessageText = editMessageText;
    }

    public DeleteMessage getDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public boolean hasDeleteMsg(){
        return deleteMessage != null;
    }

    public boolean hasSendMsg(){
        return sendMessage != null;
    }

    public boolean hasEditMsg(){
        return editMessageText != null;
    }


}
