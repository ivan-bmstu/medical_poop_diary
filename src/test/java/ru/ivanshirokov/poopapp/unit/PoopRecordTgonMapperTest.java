package ru.ivanshirokov.poopapp.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ivanshirokov.poopapp.model.PoopRecord;
import ru.ivanshirokov.poopapp.util.tgon.PoopRecordTgonMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PoopRecordTgonMapperTest {

    PoopRecord pr1;
    PoopRecordTgonMapper mapper;

    @BeforeEach
    public void poopRecordInit() {
        mapper = new PoopRecordTgonMapper();

        pr1 = new PoopRecord();
        pr1.setBlood(1);
        pr1.setMucus(9);
        pr1.setDateTime(LocalDateTime.of(2000, 1, 1, 0, 0));

    }

    @Test
    public void mapPoopRecordToTgonThenParseThisTgonToAnotherPoopRecordMustEqualsValues(){
        String pr1Tgon = mapper.mapToTgon(pr1);

        PoopRecord pr2 = new PoopRecord();
        mapper.updateModelFromTgon(pr2, pr1Tgon);

        assertEquals(pr1, pr2);
    }

    @Test
    public void mapToTgonWithOneNewValueMustGivePoopRecordWithNewValue() {//TODO по хорошему разбить на разные методы надо т.к. потом еще и дату надо будет парсить на новую
        String pr1NewBLoodTgon = mapper.mapToTgonNewBlood(pr1, 5);
        pr1.setBlood(5);

        PoopRecord pr2 = new PoopRecord();
        mapper.updateModelFromTgon(pr2, pr1NewBLoodTgon);

        assertEquals(pr1, pr2);

        String pr1NewMucusTgon = mapper.mapToTgonNewMucus(pr1, 5);
        pr1.setMucus(5);

        mapper.updateModelFromTgon(pr2, pr1NewMucusTgon);

        assertEquals(pr1, pr2);

        String pr1NewStoolTgon = mapper.mapToTgonNewStool(pr1, 5);
        pr1.setStool(5);

        mapper.updateModelFromTgon(pr2, pr1NewStoolTgon);

        assertEquals(pr1, pr2);

    }
}
