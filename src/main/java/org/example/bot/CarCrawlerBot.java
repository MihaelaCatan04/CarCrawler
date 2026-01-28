package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.lang3.time.StopWatch;
import org.example.report.CarReportService;
import org.example.scraper.CarDetailsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class CarCrawlerBot implements LongPollingSingleThreadUpdateConsumer {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String BOT_TOKEN = dotenv.get("BOT_TOKEN");
    private final TelegramClient telegramClient;
    private static final StopWatch watch = new StopWatch();
    private final static Logger logger = LoggerFactory.getLogger(CarCrawlerBot.class);

    public CarCrawlerBot(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public static String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String responseText = "";

            if (messageText.equals("/start")) {
                responseText = "Welcome to CarCrawler Bot! Use /help to see available commands.";
            } else if (messageText.equals("/statistics")) {
                try {
                    CarReportService reportService = new CarReportService();
                    watch.start();
                    responseText = reportService.generateReport();
                    watch.stop();
                    logger.info("Time Elapsed: " + watch.getTime());
                } catch (IOException e) {
                    e.printStackTrace();
                    responseText = "Error generating report!";
                }
            } else if (messageText.equals("/help")) {
                responseText = "Available commands:\n" +
                        "/start - Start the bot\n" +
                        "/statistics - Get statistics report\n" +
                        "/help - Show this help message";
            } else {
                responseText = "Unknown command. Use /help to see available commands.";
            }

            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(responseText)
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}