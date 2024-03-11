package ru.ivanshirokov.poopapp.util;

import java.time.format.DateTimeFormatter;

public class UtilDateTimeFormatter {

    public final static DateTimeFormatter tgonParserFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    public final static DateTimeFormatter printFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm VV");

}
