package ru.ivanshirokov.poopapp.controllers.callback;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ivanshirokov.poopapp.controllers.TelegramCommandController;
import ru.ivanshirokov.poopapp.controllers.TgPaths;
import ru.ivanshirokov.poopapp.exceptions.InvalidCallbackQueryData;
import ru.ivanshirokov.poopapp.models.UserPrivacy;
import ru.ivanshirokov.poopapp.services.UserPrivacyService;
import ru.ivanshirokov.poopapp.views.Emoji;
import ru.ivanshirokov.poopapp.views.TelegramResponse;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserPrivacyGmtController implements TelegramCommandController<CallbackQuery, TelegramResponse> {

    private final UserPrivacyService userPrivacyService;

    public UserPrivacyGmtController(UserPrivacyService userPrivacyService,
                                    ApplicationContext context) {
        this.userPrivacyService = userPrivacyService;
        context.getBean(CallbackControllerDispatcher.class).addPath(TgPaths.USERPRIVACY_GMT, this);
    }


    @Override
    public TelegramResponse getResponse(CallbackQuery callbackQuery) {
        String value = Arrays.stream(callbackQuery.getData().split("_"))
                .filter(s -> !s.contains("/"))
                .findFirst().orElseThrow(InvalidCallbackQueryData::new);
        int gmt = Integer.parseInt(value);

        //TODO когда буду добавлять английский, тут нужно будет нативный обновлять только GMT
        UserPrivacy userPrivacy = new UserPrivacy(callbackQuery);
        userPrivacy.setGmt(gmt);

        userPrivacyService.update(userPrivacy);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(callbackQuery.getMessage().getChatId());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setText("Ваш текущий часовой пояс: GMT" + gmt);
        editMessageText.setReplyMarkup(
                InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineKeyboardButton.builder()
                                        .text("Закрыть " + Emoji.CANCEL)
                                        .callbackData(TgPaths.DELETE)
                                        .build()
                        )).build());

        var tgResponse = new TelegramResponse();
        tgResponse.addEditMessageText(editMessageText);

        return tgResponse;
    }
}
