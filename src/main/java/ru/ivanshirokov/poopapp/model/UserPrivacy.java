package ru.ivanshirokov.poopapp.model;

import jakarta.persistence.*;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Entity
@Table(name = "user_privacy")
public class UserPrivacy {

    @Id
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "language_code")
    private String languageCode = "ru";
    private int gmt;

    public UserPrivacy() {
    }

    public UserPrivacy(Message message){
        this.id = message.getFrom().getId();
        this.chatId = message.getChatId();
        this.firstName = message.getFrom().getFirstName();
        this.languageCode = message.getFrom().getLanguageCode();
    }

    public UserPrivacy(CallbackQuery callbackQuery){
        this.id = callbackQuery.getFrom().getId();
        this.firstName = callbackQuery.getFrom().getFirstName();
        this.chatId = callbackQuery.getMessage().getChatId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getGmt() {
        return gmt;
    }

    public void setGmt(int gmt) {
        this.gmt = gmt;
    }



    @Override
    public String toString() {
        return "UserPrivacy{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", gmt=" + gmt +
                '}';
    }
}
