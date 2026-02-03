package org.example.bot;

import org.example.car_model.Advert;
import org.example.car_model.CarStatistics;
import org.example.parser.StringCarParser;
import org.example.report.CarReportService;
import org.example.request.CarOptionsRequestService;
import org.example.request.SearchRequestService;
import org.example.statistics.CarStatisticsCalculator;

import java.io.IOException;
import java.util.List;

public class CarFlowHandler {
    private final CarOptionsRequestService carOptionsRequestService;
    private final KeyboardFactory keyboard;
    private final MessageService messages;
    private final SessionService sessions;

    public CarFlowHandler(CarOptionsRequestService carOptionsService,
                          KeyboardFactory keyboard,
                          MessageService messages,
                          SessionService sessionService) {

        this.carOptionsRequestService = carOptionsService;
        this.keyboard = keyboard;
        this.messages = messages;
        this.sessions = sessionService;
    }


    public void start(long chatId) {
        messages.send(chatId, "Select a Brand:", keyboard.brands());
    }

    public void onBrand(long chatId, int messageId, int brandId) throws IOException, InterruptedException {
        UserSession s = sessions.get(chatId);
        s.brandId = brandId;
        s.step = Step.MODEL;

        CarOptions options = carOptionsRequestService.fetch(brandId);
        s.carOptions = options;

        messages.edit(chatId, messageId, "Choose model:", keyboard.models(options.models));
    }

    public void onModel(long chatId, int messageId, int modelId) {
        UserSession s = sessions.get(chatId);
        s.modelId = modelId;
        s.step = Step.GENERATION;

        ModelOption selectedModel = s.carOptions.models.stream()
                .filter(m -> m.id == modelId)
                .findFirst()
                .orElse(null);

        if (selectedModel == null || selectedModel.generations.isEmpty()) {
            messages.sendText(chatId, "No generations found for this model.");
            return;
        }

        messages.edit(chatId, messageId, "Choose generation:",
                keyboard.generations(selectedModel.generations));
    }

    public void onGeneration(long chatId, int messageId, int generationId) {
        UserSession s = sessions.get(chatId);
        s.generationId = generationId;
        s.step = Step.MIN_YEAR;
        messages.sendText(chatId, "Please enter minimum fabrication year:");
    }

    public void onText(long chatId, String text) {
        UserSession s = sessions.get(chatId);
        try {
            switch (s.step) {
                case MIN_YEAR -> {
                    s.minYear = Integer.parseInt(text);
                    s.step = Step.MAX_YEAR;
                    messages.sendText(chatId, "Enter maximum fabrication year:");
                }
                case MAX_YEAR -> {
                    s.maxYear = Integer.parseInt(text);
                    s.step = Step.MIN_MILEAGE;
                    messages.sendText(chatId, "Enter minimum mileage:");
                }
                case MIN_MILEAGE -> {
                    s.minMileage = Integer.parseInt(text);
                    s.step = Step.MAX_MILEAGE;
                    messages.sendText(chatId, "Enter maximum mileage:");
                }
                case MAX_MILEAGE -> {
                    s.maxMileage = Integer.parseInt(text);

                    SearchRequestService searchRequestService = new SearchRequestService();
                    StringCarParser parser = new StringCarParser();
                    String body = searchRequestService.search(s);
                    List<Advert> cars = parser.parse(body);
                    CarStatistics result = new CarStatisticsCalculator().calculate(cars);
                    messages.sendText(chatId, CarReportService.generateReport(result));

                    s.step = Step.BRAND;
                }
            }
        } catch (NumberFormatException e) {
            messages.sendText(chatId, "Please enter a valid number.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}