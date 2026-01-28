package org.example.main;

import org.example.bot.CarCrawlerBot;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        try {
            String botToken = CarCrawlerBot.getBotToken();
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            TelegramClient telegramClient = new OkHttpTelegramClient(botToken);
            CarCrawlerBot bot = new CarCrawlerBot(telegramClient);
            botsApplication.registerBot(botToken, bot);

            logger.info("CarCrawlerBot started successfully!");
            logger.info("Bot is running... Press Ctrl+C to stop.");

        } catch (TelegramApiException e) {
            System.err.println("Failed to start bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}