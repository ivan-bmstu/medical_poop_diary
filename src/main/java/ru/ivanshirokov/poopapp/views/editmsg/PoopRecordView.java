package ru.ivanshirokov.poopapp.views.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.models.PoopRecord;
import ru.ivanshirokov.poopapp.util.UtilDateTimeFormatter;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;
import ru.ivanshirokov.poopapp.views.Emoji;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class PoopRecordView extends EditMessageText {

    private final PoopRecordTgonMapper poopRecordTgonMapper;
    private final PoopRecord poopRecord;

    public PoopRecordView(PoopRecord poopRecord) {
        this.poopRecord = poopRecord;
        this.poopRecordTgonMapper = new PoopRecordTgonMapper();
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText() {
        String border = "========================\n";

        String textInit =
                "Если не указать ни одного параметра, то будет записано только время " + Emoji.POOP + "\n"
                + Emoji.BLOOD + " - кровь в стуле: 0 - отсутствует, 9 - много\n"
                + Emoji.MUCUS + " - слизь в стуле: 0 - отсутствует, 9 - много\n"
                + Emoji.SOLID + " - твердость стула: 0 - жидкий, 4 - нормальный, 9 - твердый\n";


        LocalDateTime ldt = poopRecord.getDateTime().plusHours(poopRecord.getUserPrivacy().getGmt());
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("GMT+" + poopRecord.getUserPrivacy().getGmt()));

        String dateTime = "Время записи: " + zdt.format(UtilDateTimeFormatter.printFormatter) + "\n";


        String bloodPrint = printScale(poopRecord.getBlood(), Emoji.BLOOD);

        String mucusPrint = printScale(poopRecord.getMucus(), Emoji.MUCUS);

        String stoolPrint = printScale(poopRecord.getStool(), Emoji.SOLID);

        return textInit + border + dateTime + bloodPrint + mucusPrint + stoolPrint + border;
    }

    private InlineKeyboardMarkup getKeyboard() {
        String completeBtnText = "Завершить запись " + Emoji.CHECK_MARK;
        String completeBtnCallbackData = TgPaths.POOPRECORD_SAVE + poopRecordTgonMapper.mapToTgon(poopRecord);

        String bloodBtnText = "Внести данные об " + Emoji.BLOOD;
        String bloodBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordTgonMapper.mapToTgonNewBlood(poopRecord, -2);
        if (poopRecord.getBlood() >= 0) {
            bloodBtnText = "Убрать данные об " + Emoji.BLOOD;
            bloodBtnCallbackData = TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewBlood(poopRecord, -1);
        }

        String mucusBtnText = "Внести данные об " + Emoji.MUCUS;
        String mucusBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordTgonMapper.mapToTgonNewMucus(poopRecord, -2);
        if (poopRecord.getMucus() >= 0) {
            mucusBtnText = "Убрать данные об " + Emoji.MUCUS;
            mucusBtnCallbackData = TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewMucus(poopRecord, -1);
        }

        String stoolBtnText = "Внести данные об " + Emoji.SOLID;
        String stoolBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordTgonMapper.mapToTgonNewStool(poopRecord, -2);
        if (poopRecord.getStool() >= 0) {
            stoolBtnText = "Убрать данные об " + Emoji.SOLID;
            stoolBtnCallbackData = TgPaths.POOPRECORD + poopRecordTgonMapper.mapToTgonNewStool(poopRecord, -1);
        }

        String deleteBtnText = "Отмена " + Emoji.CANCEL;

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(bloodBtnText)
                                .callbackData(bloodBtnCallbackData)
                                .build()))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(mucusBtnText)
                                .callbackData(mucusBtnCallbackData)
                                .build()))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(stoolBtnText)
                                .callbackData(stoolBtnCallbackData)
                                .build()))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(completeBtnText)
                                .callbackData(completeBtnCallbackData)
                                .build()))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(deleteBtnText)
                                .callbackData(TgPaths.DELETE)
                                .build()
                )).build();
    }

    private String printScale(int val, String emojiCode) {
        if (val < 0) {
            return "";
        }
        return emojiCode + " " + Emoji.SCORE.repeat(val) + Emoji.EMPTY_SCORE.repeat(9 - val)+ "\n";
    }
}
