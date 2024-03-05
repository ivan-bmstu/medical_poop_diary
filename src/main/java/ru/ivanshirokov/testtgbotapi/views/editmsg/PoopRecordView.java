package ru.ivanshirokov.testtgbotapi.views.editmsg;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.services.PoopRecordService;
import ru.ivanshirokov.testtgbotapi.views.Emoji;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class PoopRecordView extends EditMessageText {

    private final PoopRecordService poopRecordService;
    private final PoopRecord poopRecord;

    public PoopRecordView(PoopRecord poopRecord, PoopRecordService poopRecordService) {
        this.poopRecord = poopRecord;
        this.poopRecordService = poopRecordService;
        setText(getMsgText());
        setReplyMarkup(getKeyboard());
    }

    private String getMsgText() {
        String textInit =
                "Если не указать ни одного параметра, то будет записано только время " + Emoji.POOP + "\n"
                + Emoji.BLOOD + " - кровь в стула: 0 - отсутствует, 9 - много\n"
                + Emoji.MUCUS + " - слизь в стула: 0 - отсутствует, 9 - много\n"
                + Emoji.SOLID + " - твердость стула: 0 - жидкий, 4 - нормальный, 9 - твердый\n";

//        String textRecord = "Ваша запись " + Emoji.POOP + ":\n";

        String border = "========================================\n";

        LocalDateTime ldt = poopRecord.getDateTime().plusHours(3);
//        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("GMT+3"));//TODO надо брать gmt из user_privacy, сделать это кешируемым

        String dt = "Время записи: " + ldt.format(poopRecordService.printFormatter) + "\n";


        String bloodPrint = printScale(poopRecord.getBlood(), Emoji.BLOOD);

        String mucusPrint = printScale(poopRecord.getMucus(), Emoji.MUCUS);

        String stoolPrint = printScale(poopRecord.getStool(), Emoji.SOLID);

        String textMsg = textInit;
//        if (poopRecordService.isDateOnly(poopRecord)){
//            textMsg = textInit;
//        }

        textMsg = textMsg + border + dt + bloodPrint + mucusPrint + stoolPrint + border;
        return textMsg;
    }

    private InlineKeyboardMarkup getKeyboard() {
        String completeBtnText = "Завершить запись " + Emoji.CHECK_MARK; //TODO продолжить
        String completeBtnCallbackData = TgPaths.POOPRECORD_SAVE + poopRecordService.mapToTgon(poopRecord);

        String bloodBtnText = "Внести данные об " + Emoji.BLOOD;
        String bloodBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordService.mapToTgonNewBlood(poopRecord, -2);
        if (poopRecord.getBlood() >= 0) {
            bloodBtnText = "Убрать данные об " + Emoji.BLOOD;
            bloodBtnCallbackData = TgPaths.POOPRECORD + poopRecordService.mapToTgonNewBlood(poopRecord, -1);
        }

        String mucusBtnText = "Внести данные об " + Emoji.MUCUS;
        String mucusBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordService.mapToTgonNewMucus(poopRecord, -2);
        if (poopRecord.getMucus() >= 0) {
            mucusBtnText = "Убрать данные об " + Emoji.MUCUS;
            mucusBtnCallbackData = TgPaths.POOPRECORD + poopRecordService.mapToTgonNewMucus(poopRecord, -1);
        }

        String stoolBtnText = "Внести данные об " + Emoji.SOLID;
        String stoolBtnCallbackData = TgPaths.POOPRECORD_INPUT + poopRecordService.mapToTgonNewStool(poopRecord, -2);
        if (poopRecord.getStool() >= 0) {
            stoolBtnText = "Убрать данные об " + Emoji.SOLID;
            stoolBtnCallbackData = TgPaths.POOPRECORD + poopRecordService.mapToTgonNewStool(poopRecord, -1);
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
        String score = "◽\uFE0F";
        String emptyScore = "◾\uFE0F";
        return emojiCode + " " + score.repeat(val) + emptyScore.repeat(9 - val)+ "\n";
    }
}
