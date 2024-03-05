package ru.ivanshirokov.testtgbotapi.controllers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCommandController<Request, Response>{
    
    Response getResponse(Request request);
}
