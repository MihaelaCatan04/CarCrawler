package org.example.main;

import org.example.bot.*;
import org.example.request.CarOptionsRequestService;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Main {
    public static void main(String[] args) {
        String botToken = CarCrawlerBot.getBotToken();

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {

            TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

            CarOptionsRequestService carOptionsRequestService = new CarOptionsRequestService();
            KeyboardFactory keyboardFactory = new KeyboardFactory();
            MessageService messageService = new MessageService(telegramClient);
            SessionService sessionService = new SessionService();

            CarFlowHandler flowHandler = new CarFlowHandler(
                    carOptionsRequestService,
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