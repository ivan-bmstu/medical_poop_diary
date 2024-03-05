package ru.ivanshirokov.testtgbotapi.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ivanshirokov.testtgbotapi.models.PoopRecord;
import ru.ivanshirokov.testtgbotapi.models.UserPrivacy;
import ru.ivanshirokov.testtgbotapi.repositories.PoopRecordRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PoopRecordService implements TgonParser<PoopRecord> {

    public final DateTimeFormatter dtParserFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public final DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");



    private final PoopRecordRepository poopRecordRepository;

    public PoopRecordService(PoopRecordRepository poopRecordRepository) {
        this.poopRecordRepository = poopRecordRepository;
    }

    @Override
    public String mapToTgon(PoopRecord poopRecord) {

        return "_bl:" + poopRecord.getBlood()
                + "_mu:" + poopRecord.getMucus()
                + "_st:" + poopRecord.getStool()
                + "_da:" + poopRecord.getDateTime().format(dtParserFormatter);
    }

    @Override
    public PoopRecord updateModelFromTgon(PoopRecord poopRecord, String tgon) {
        List<String> values = getValues(tgon);

        poopRecord.setBlood(Integer.parseInt(values.get(0)));
        poopRecord.setMucus(Integer.parseInt(values.get(1)));
        poopRecord.setStool(Integer.parseInt(values.get(2)));

        String date = values.get(3);
        LocalDateTime localDateTime = LocalDateTime.parse(date, dtParserFormatter);

        poopRecord.setDateTime(localDateTime);

        return poopRecord;
    }

    public PoopRecord getCopy(PoopRecord from){
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

    public void save(PoopRecord poopRecord){
        poopRecordRepository.save(poopRecord);
    }

    public long getCountByUser(UserPrivacy userPrivacy){
        return poopRecordRepository.countByUserPrivacyId(userPrivacy.getId());
    }

    public boolean isDateOnly(PoopRecord poopRecord){
        return (poopRecord.getBlood() < 0 && poopRecord.getMucus() < 0 && poopRecord.getStool() < 0);
    }

    public List<PoopRecord> getPoopRecords(long userPrivacyId, int page){
        Pageable sortPageByDate = PageRequest.of(page, 5);
        return poopRecordRepository.findAllByUserPrivacyId(userPrivacyId, sortPageByDate);
    }
}
