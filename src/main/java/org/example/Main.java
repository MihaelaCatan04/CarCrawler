package org.example;

import org.example.service.options.CarOptionsService;
import org.example.telegram.CarCrawlerBot;
import org.example.telegram.handler.CarFlowHandler;
import org.example.telegram.keyboard.KeyboardFactory;
import org.example.telegram.message.MessageService;
import org.example.telegram.session.SessionService;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Main {
    public static void main(String[] args) {
        String botToken = CarCrawlerBot.getBotToken();

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {

            TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

            CarOptionsService carOptionsService = new CarOptionsService();
            KeyboardFactory keyboardFactory = new KeyboardFactory();
            MessageService messageService = new MessageService(telegramClient);
            SessionService sessionService = new SessionService();

            CarFlowHandler flowHandler = new CarFlowHandler(
                    carOptionsService,
                    keyboardFactory,
                    messageService,
                    sessionService
            );

            CarCrawlerBot bot = new CarCrawlerBot(flowHandler);
            botsApplication.registerBot(botToken, bot);

            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
