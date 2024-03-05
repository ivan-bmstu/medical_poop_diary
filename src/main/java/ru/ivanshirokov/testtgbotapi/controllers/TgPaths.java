package ru.ivanshirokov.testtgbotapi.controllers;

import ru.ivanshirokov.testtgbotapi.views.Emoji;

public class TgPaths {

    public static final String POOPRECORD = "/pooprecord";
    public static final String POOPRECORD_INPUT = "/pooprecord/input";
    public static final String DELETE = "/delete";
    public static final String UNKNOWN = "/unknown";
    public static final String START = "/start";
    public static final String POOPRECORD_INIT = "Сделать запись " + Emoji.TOILET;
    public static final String POOPRECORD_SAVE = "/pooprecord/save";
    public static final String POOPRECORD_LIST_INIT = "Посмотреть свои записи";
    public static final String POOPRECORD_LIST = "/pooprecord/list";
}
