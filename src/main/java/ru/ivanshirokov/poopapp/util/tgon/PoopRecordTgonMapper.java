package ru.ivanshirokov.poopapp.util.tgon;

import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.util.UtilDateTimeFormatter;

import java.time.LocalDateTime;
import java.util.List;

public class PoopRecordTgonMapper implements TgonParser<PoopRecord> {

    @Override
    public String mapToTgon(PoopRecord poopRecord) {
        return "_bl:" + poopRecord.getBlood()
                + "_mu:" + poopRecord.getMucus()
                + "_st:" + poopRecord.getStool()
                + "_da:" + poopRecord.getDateTime().format(UtilDateTimeFormatter.tgonParserFormatter);
    }

    @Override
    public PoopRecord updateModelFromTgon(PoopRecord poopRecord, String tgon) {
        List<String> values = getValues(tgon);

        poopRecord.setBlood(Integer.parseInt(values.get(0)));
        poopRecord.setMucus(Integer.parseInt(values.get(1)));
        poopRecord.setStool(Integer.parseInt(values.get(2)));

        String date = values.get(3);
        LocalDateTime localDateTime = LocalDateTime.parse(date, UtilDateTimeFormatter.tgonParserFormatter);

        poopRecord.setDateTime(localDateTime);

        return poopRecord;
    }

    private PoopRecord getCopy(PoopRecord from){
        var to = new PoopRecord();
        to.setBlood(from.getBlood());
        to.setMucus(from.getMucus());
        to.setStool(from.getStool());
        to.setDateTime(from.getDateTime());
        return to;
    }

    public String mapToTgonNewBlood(PoopRecord poopRecord, int val) {
        var temp = getCopy(poopRecord);
        temp.setBlood(val);
        return mapToTgon(temp);
    }

    public String mapToTgonNewMucus(PoopRecord poopRecord, int val) {
        var temp = getCopy(poopRecord);
        temp.setMucus(val);
        return mapToTgon(temp);
    }

    public String mapToTgonNewStool(PoopRecord poopRecord, int val){
        var temp = getCopy(poopRecord);
        temp.setStool(val);
        return  mapToTgon(temp);
    }
}
