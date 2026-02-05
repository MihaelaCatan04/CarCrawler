package org.example.bot;

import org.example.request.CarOptionsRequestService;

import java.io.IOException;

public class CarFlowHandler {
    private final CarOptionsRequestService carOptionsRequestService;

    private final KeyboardFactory keyboard;
    private final MessageService messages;
    private final SessionService sessions;

    public CarFlowHandler(CarOptionsRequestService carOptionsService, KeyboardFactory keyboard, MessageService messages, SessionService sessionService) {

        this.carOptionsRequestService = carOptionsService;
        this.keyboard = keyboard;
        this.messages = messages;
        this.sessions = sessionService;
    }

    public void start(long chatId) {
        getMessages().send(chatId, "Select a Brand:", getKeyboard().brands());
    }

    public void onBrand(long chatId, int messageId, int brandId) throws IOException, InterruptedException {
        UserSession s = getSessions().get(chatId);
        s.brandId = brandId;
        s.step = Step.MODEL;

        CarOptions options = getCarOptionsRequestService().fetch(brandId);
        s.carOptions = options;

        getMessages().edit(chatId, messageId, "Choose model:", getKeyboard().models(options.getModels()));
    }

    public void onModel(long chatId, int messageId, int modelId) {
        UserSession s = getSessions().get(chatId);
        s.modelId = modelId;
        s.step = Step.GENERATION;

        ModelOption selectedModel = s.carOptions.getModels().stream().filter(m -> m.getId() == modelId).findFirst().orElse(null);

        if (selectedModel == null || selectedModel.getGenerations().isEmpty()) {
            getMessages().sendText(chatId, "No generations found for this model.");
            return;
        }

        getMessages().edit(chatId, messageId, "Choose generation:", getKeyboard().generations(selectedModel.getGenerations()));
    }

    public void onGeneration(long chatId, int messageId, int generationId) {
        UserSession s = getSessions().get(chatId);
        s.generationId = generationId;
        s.step = Step.MIN_YEAR;
        getMessages().sendText(chatId, "Please enter minimum fabrication year:");
    }

    public void onText(long chatId, String text) {
        UserSession s = getSessions().get(chatId);
        try {
            s.step.processStep(s, chatId, text, getMessages());
        } catch (NumberFormatException e) {
            getMessages().sendText(chatId, "Please enter a valid number.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyboardFactory getKeyboard() {
        return keyboard;
    }

    private CarOptionsRequestService getCarOptionsRequestService() {
        return carOptionsRequestService;
    }

    private MessageService getMessages() {
        return messages;
    }

    private SessionService getSessions() {
        return sessions;
    }

}