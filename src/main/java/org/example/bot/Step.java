package org.example.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.car_model.Advert;
import org.example.car_model.CarStatistics;
import org.example.parser.StringCarParser;
import org.example.report.CarReportService;
import org.example.request.SearchRequestService;
import org.example.statistics.CarStatisticsCalculator;

import java.io.IOException;
import java.util.List;

public enum Step {
    BRAND, MODEL, GENERATION,

    MIN_YEAR {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) {
            s.minYear = Integer.parseInt(text);
            s.step = Step.MAX_YEAR;
            messageService.sendText(chatId, "Enter maximum fabrication year:");
        }
    }, MAX_YEAR {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) {
            s.maxYear = Integer.parseInt(text);
            s.step = Step.MIN_MILEAGE;
            messageService.sendText(chatId, "Enter minimum mileage:");
        }
    }, MIN_MILEAGE {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) {
            s.minMileage = Integer.parseInt(text);
            s.step = Step.MAX_MILEAGE;
            messageService.sendText(chatId, "Enter maximum mileage:");
        }
    }, MAX_MILEAGE {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) throws IOException, InterruptedException, IOException {
            s.maxMileage = Integer.parseInt(text);
            SearchRequestService searchRequestService = new SearchRequestService();
            StringCarParser parser = new StringCarParser();
            String body = searchRequestService.search(s);
            List<Advert> cars = parser.parse(body);
            CarStatistics result = new CarStatisticsCalculator().calculate(cars);
            messageService.sendText(chatId, CarReportService.generateReport(result));
            s.step = Step.BRAND;
        }
    };

    public void processStep(UserSession s, long chatId, String text, MessageService messageService) throws IOException, InterruptedException, JsonProcessingException {
        messageService.sendText(chatId, "Step " + this.name() + " doesn't have any further processing.");
    }
}
