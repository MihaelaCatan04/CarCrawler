package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public class CarCrawlerBot implements LongPollingSingleThreadUpdateConsumer {
    private final CarFlowHandler flow;

    public CarCrawlerBot(CarFlowHandler flow) {
        this.flow = flow;
    }

    public static String getBotToken() {
        return Dotenv.load().get("BOT_TOKEN");
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if (text.equals("/start") || text.equals("/cars")) {
                flow.start(chatId);
            } else {
                flow.onText(chatId, text);
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            CallbackData cb = CallbackData.parse(update.getCallbackQuery().getData());

            switch (cb.action) {
                case "BRAND" -> {
                    try {
                        flow.onBrand(chatId, messageId, Integer.parseInt(cb.args[0]));
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "MODEL" -> flow.onModel(chatId, messageId, Integer.parseInt(cb.args[0]));
                case "GEN" -> flow.onGeneration(chatId, messageId, Integer.parseInt(cb.args[0]));
            }
        }
    }
}