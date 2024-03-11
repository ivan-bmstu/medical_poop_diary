package ru.ivanshirokov.poopapp.controllers;

public interface TelegramCommandController<Request, Response>{
    
    Response getResponse(Request request);
}
