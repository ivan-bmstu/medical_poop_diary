package ru.ivanshirokov.testtgbotapi.controllers.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ivanshirokov.testtgbotapi.controllers.TelegramCommandController;
import ru.ivanshirokov.testtgbotapi.views.TelegramResponse;

import java.util.*;

@Component
public class CallbackControllerDispatcher {
    private final Map<String, TelegramCommandController<CallbackQuery, TelegramResponse>> mapPathController = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(CallbackControllerDispatcher.class);

    public TelegramResponse getResponse(CallbackQuery callbackQuery){

        List<String> paths = Arrays.stream(callbackQuery.getData().split("_"))
                .filter(s -> s.contains("/"))
                .toList();

        String path = paths.getFirst();
        if (!mapPathController.containsKey(path)){
            logger.error("ERROR: callback have invalid error, error in code!");
            throw new RuntimeException();
        }

        var controller = mapPathController.get(path);
        return controller.getResponse(callbackQuery);//TODO подумать про /путьмодели*метод_p1:val_p2:val
    }

    public void addPath(String path, TelegramCommandController<CallbackQuery, TelegramResponse> controller){
        mapPathController.put(path,controller);
    }

}
