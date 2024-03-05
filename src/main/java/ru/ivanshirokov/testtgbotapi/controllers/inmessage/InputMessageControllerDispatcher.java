package ru.ivanshirokov.testtgbotapi.controllers.inmessage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.controllers.TgPaths;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

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
        return controller.getResponse(inputMsg);
    }

    public void addPath(String path, TelegramCommandController<Message, TelegramResponse> controller){
        mapPathController.put(path,controller);
    }
}
