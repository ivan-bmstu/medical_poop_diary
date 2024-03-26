package ru.ivanshirokov.poopapp.controller.inmessage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.poopapp.controller.TelegramCommandController;
import ru.ivanshirokov.poopapp.controller.TgPaths;
import ru.ivanshirokov.poopapp.view.TelegramResponse;

import java.util.*;

@Component
public class InputMessageControllerDispatcher {

    private final Map<String, TelegramCommandController<Message, TelegramResponse>> mapPathController = new HashMap<>();

    public TelegramResponse getResponse(Message inputMsg){

        var msgText = inputMsg.getText();
        var controller = mapPathController.get(TgPaths.UNKNOWN);
        if (mapPathController.containsKey(inputMsg.getText())){
            controller = mapPathController.get(msgText);
        }
        if (msgText.startsWith("/feedback")) {
            controller = mapPathController.get(TgPaths.FEEDBACK);
        }

        return controller.getResponse(inputMsg);
    }

    public void addPath(String path, TelegramCommandController<Message, TelegramResponse> controller){
        mapPathController.put(path,controller);
    }
}
