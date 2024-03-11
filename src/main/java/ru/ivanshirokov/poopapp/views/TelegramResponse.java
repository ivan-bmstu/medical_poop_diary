package ru.ivanshirokov.poopapp.views;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.ArrayList;
import java.util.List;

public class TelegramResponse {

    private final List<SendMessage> sendMessages = new ArrayList<>();
    private final List<EditMessageText> editMessagesText = new ArrayList<>();
    private final List<DeleteMessage> deleteMessages = new ArrayList<>();

    public List<SendMessage> getSendMessages() {
        return sendMessages;
    }

    public void addSendMessage(SendMessage sendMessage) {
        sendMessages.add(sendMessage);
    }

    public List<EditMessageText> getEditMessagesText() {
        return editMessagesText;
    }

    public void addEditMessageText(EditMessageText editMessageText) {
        editMessagesText.add(editMessageText);
    }

    public List<DeleteMessage> getDeleteMessages() {
        return deleteMessages;
    }

    public void addDeleteMessage(DeleteMessage deleteMessage) {
        deleteMessages.add(deleteMessage);
    }

    public boolean hasDeleteMsg(){
        return !deleteMessages.isEmpty();
    }

    public boolean hasSendMsg(){
        return !sendMessages.isEmpty();
    }

    public boolean hasEditMsg(){
        return !editMessagesText.isEmpty();
    }
}
