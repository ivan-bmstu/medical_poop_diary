package ru.ivanshirokov.poopapp.controllers.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.exceptions.InvalidCallbackQueryData;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

import java.util.*;

@Component
public class CallbackControllerDispatcher {
    private final Map<String, TelegramCommandController<CallbackQuery, TelegramResponse>> mapPathController = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CallbackControllerDispatcher.class);

    public TelegramResponse getResponse(CallbackQuery callbackQuery){

        String path = Arrays.stream(callbackQuery.getData().split("_"))
                .filter(s -> s.contains("/"))
                .findFirst().orElse("ERROR - must get exception");

        if (!mapPathController.containsKey(path)){
            logger.error("ERROR: callback have invalid error, error in code!");
            throw new InvalidCallbackQueryData();
        }

        var controller = mapPathController.get(path);
        return controller.getResponse(callbackQuery);
    }

    public void addPath(String path, TelegramCommandController<CallbackQuery, TelegramResponse> controller){
        mapPathController.put(path,controller);
    }

}
