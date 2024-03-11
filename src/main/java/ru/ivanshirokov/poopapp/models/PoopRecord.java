package ru.ivanshirokov.poopapp.models;

import jakarta.persistence.*;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.*;

//val -1 ===> N/A val
//val -2 ===> need input val - ONLY FOR CALLBACKQUERY DATA, NOT FOR DB
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
    @ManyToOne(fetch = FetchType.LAZY) //TODO переделать на LAZY + cache
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

    public void setUserPrivacy(UserPrivacy userPrivacy) {
        this.userPrivacy = userPrivacy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoopRecord that = (PoopRecord) o;

        if (getId() != that.getId()) return false;
        if (getBlood() != that.getBlood()) return false;
        if (getMucus() != that.getMucus()) return false;
        if (getStool() != that.getStool()) return false;
        if (getDateTime() != null ? !getDateTime().equals(that.getDateTime()) : that.getDateTime() != null)
            return false;
        return getUserPrivacy() != null ? getUserPrivacy().equals(that.getUserPrivacy()) : that.getUserPrivacy() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getBlood();
        result = 31 * result + getMucus();
        result = 31 * result + getStool();
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        return result;
    }
}
