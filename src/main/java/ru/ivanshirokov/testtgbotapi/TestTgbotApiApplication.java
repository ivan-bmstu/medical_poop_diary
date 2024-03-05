package ru.ivanshirokov.testtgbotapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TestTgbotApiApplication {

	private static Logger logger = LoggerFactory.getLogger("MAIN");

	public static void main(String[] args) {
		try {
			var ctx = SpringApplication.run(TestTgbotApiApplication.class, args);
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(ctx.getBean("tgBot", TelegramLongPollingBot.class));
		} catch (TelegramApiException e) {
			logger.debug("ERROR WHEN INITIALIZE BOT: BOT SESSION CANNOT BE NULL");
            throw new RuntimeException(e);
        }
    }

}
