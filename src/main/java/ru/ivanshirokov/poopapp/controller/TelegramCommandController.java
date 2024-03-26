package ru.ivanshirokov.poopapp.controller;

public interface TelegramCommandController<Request, Response>{
    
    Response getResponse(Request request);
}
