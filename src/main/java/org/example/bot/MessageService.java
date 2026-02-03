package org.example.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MessageService {
    private final TelegramClient client;

    public MessageService(TelegramClient client) {
        this.client = client;
    }

    public void send(long chatId, String text, InlineKeyboardMarkup kb) {
        try {
            client.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .replyMarkup(kb)
                            .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendText(long chatId, String text) {
        try {
            client.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public void edit(long chatId, int messageId, String text, InlineKeyboardMarkup kb) {
        try {
            client.execute(
                    EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text(text)
                            .replyMarkup(kb)
                            .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void editText(long chatId, int messageId, String text) {
        try {
            client.execute(
                    EditMessageText.builder()
                            .chatId(chatId)
                            .messageId(messageId)
                            .text(text)
                            .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
