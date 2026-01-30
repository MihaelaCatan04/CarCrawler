package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.lang3.time.StopWatch;
import org.example.model.Advert;
import org.example.model.CarStatistics;
import org.example.parser.StringCarParser;
import org.example.report.CarReportService;
import org.example.scraper.SearchService;
import org.example.statistics.CarStatisticsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CarCrawlerBot implements LongPollingSingleThreadUpdateConsumer {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String BOT_TOKEN = dotenv.get("BOT_TOKEN");
    private static final StopWatch watch = new StopWatch();
    private final static Logger logger = LoggerFactory.getLogger(CarCrawlerBot.class);
    private final static String START_MESSAGE = "Welcome to CarCrawler Bot! Use /help to see available commands.";
    private final static String HELP_MESSAGE = """
              Available commands:
              /start - Start the bot
              /statistics - Get statistics report
              /help - Show this help message
            """;
    private final static String UNKNOWN_COMMAND_MESSAGE = "Unknown command. Use /help to see available commands.";
    private final TelegramClient telegramClient;
    private final Map<String, Supplier<String>> commandHandlers = new HashMap<>();

    public CarCrawlerBot(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;

        commandHandlers.put("/start", this::reply_start);
        commandHandlers.put("/help", () -> HELP_MESSAGE);
        commandHandlers.put("/statistics", () -> {
            try {
                return reply_statistics();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static String getBotToken() {
        return BOT_TOKEN;
    }

    private String reply_start() {
        return START_MESSAGE;
    }

    private String reply_statistics() throws IOException, InterruptedException {
        watch.start();
        SearchService service = new SearchService();
        String allData = service.search();
        StringCarParser parser = new StringCarParser();
        List<Advert> cars = parser.parse(allData);
        CarStatisticsCalculator calculator = new CarStatisticsCalculator();
        CarStatistics result = calculator.calculate(cars);
        String responseText = CarReportService.generateReport(result);
        watch.stop();
        logger.info("Time Elapsed: " + watch.getTime());
        watch.reset();
        return responseText;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String responseText = commandHandlers.getOrDefault(messageText, () -> UNKNOWN_COMMAND_MESSAGE).get();

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