package ru.ivanshirokov.testtgbotapi.models;

public enum LanguageCode {
    EN("en"), RU("ru");

    private String code;

    LanguageCode(String code){
        this.code = code;
    }

    public String code(){
        return code;
    }
}
