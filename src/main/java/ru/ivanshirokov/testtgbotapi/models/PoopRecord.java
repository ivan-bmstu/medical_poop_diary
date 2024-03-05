package ru.ivanshirokov.testtgbotapi.models;

import jakarta.persistence.*;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.*;

//val -1 ===> non val
//val -2 ===> need input val
//when insert into db all val < 0 ===> NULL
@Entity
public class PoopRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int blood = -1;
    private int mucus = -1;
    private int stool = -1;

    @Column(name = "date")
    private LocalDateTime dateTime;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserPrivacy userPrivacy;


    public PoopRecord() {
    }

    public PoopRecord(Message message){
        userPrivacy = new UserPrivacy(message);
        dateTime =
                LocalDateTime.ofEpochSecond(message.getDate(), 0, ZoneOffset.UTC);
    }

    public PoopRecord(CallbackQuery callbackQuery){
        userPrivacy = new UserPrivacy(callbackQuery);
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getMucus() {
        return mucus;
    }

    public void setMucus(int mucus) {
        this.mucus = mucus;
    }

    public int getId() {
        return id;
    }

    public UserPrivacy getUserPrivacy() {
        return userPrivacy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime timestamp) {
        this.dateTime = timestamp;
    }

    public int getStool() {
        return stool;
    }

    public void setStool(int stool) {
        this.stool = stool;
    }

    @Override
    public String toString() {
        return "PoopRecord{" +
                "id=" + id +
                ", blood=" + blood +
                ", mucus=" + mucus +
                ", stool=" + stool +
                ", dateTime=" + dateTime +
                ", userPrivacy=" + userPrivacy +
                '}';
    }
}
