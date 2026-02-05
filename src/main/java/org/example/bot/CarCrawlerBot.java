package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;


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
            consumeEmptyUpdate(update);
        } else if (update.hasCallbackQuery()) {
            consumeCallbackUpdate(update);
        }
    }

    private void consumeEmptyUpdate(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if (text.equals("/start") || text.equals("/cars")) {
            getFlow().start(chatId);
        } else {
            getFlow().onText(chatId, text);
        }
    }

    private void consumeCallbackUpdate(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        CallbackData cb = new CallbackData(update.getCallbackQuery().getData());
        cb.getAction().flowRun(getFlow(), chatId, messageId, cb);
    }

    private CarFlowHandler getFlow() {
        return flow;
    }

}