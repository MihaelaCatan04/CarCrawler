package org.example.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;

public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final TelegramClient client;

    public MessageService(TelegramClient client) {
        this.client = client;
    }

    private <T extends Serializable> void executeSafely(BotApiMethod<T> method, String errorMessage) {
        try {
            client.execute(method);
        } catch (TelegramApiException e) {
            logger.error(errorMessage, e);
        }
    }

    public void send(long chatId, String text, InlineKeyboardMarkup kb) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(kb)
                .build();

        executeSafely(message, String.format("Failed to send message with keyboard to chatId=%d", chatId));
    }

    public void sendText(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        executeSafely(message, String.format("Failed to send text message to chatId=%d", chatId));
    }

    public void edit(long chatId, int messageId, String text, InlineKeyboardMarkup kb) {
        EditMessageText editMessage = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(text)
                .replyMarkup(kb)
                .build();

        executeSafely(editMessage, String.format("Failed to edit messageId=%d in chatId=%d", messageId, chatId));
    }
}
