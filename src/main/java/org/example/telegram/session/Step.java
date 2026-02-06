package org.example.telegram.session;

import org.example.model.entity.Advert;
import org.example.model.entity.CarStatistics;
import org.example.model.entity.UserSession;
import org.example.service.parser.StringCarParser;
import org.example.service.report.CarReportService;
import org.example.service.search.SearchService;
import org.example.service.statistics.CarStatisticsCalculator;
import org.example.telegram.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    },
    MAX_YEAR {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) {
            s.maxYear = Integer.parseInt(text);
            s.step = Step.MIN_MILEAGE;
            messageService.sendText(chatId, "Enter minimum mileage:");
        }
    },
    MIN_MILEAGE {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) {
            s.minMileage = Integer.parseInt(text);
            s.step = Step.MAX_MILEAGE;
            messageService.sendText(chatId, "Enter maximum mileage:");
        }
    },
    MAX_MILEAGE {
        @Override
        public void processStep(UserSession s, long chatId, String text, MessageService messageService) throws IOException, InterruptedException {
            Logger logger = LoggerFactory.getLogger(Step.class);
            s.maxMileage = Integer.parseInt(text);
            SearchService searchService = new SearchService();
            StringCarParser parser = new StringCarParser();
            String body = searchService.search(s);
            List<Advert> cars = parser.parse(body);
            CarStatistics result = new CarStatisticsCalculator().calculate(cars);
            messageService.sendText(chatId, CarReportService.generateReport(result));
            s.markEnd();
            logger.info("Time Elapsed: {}", s.getDurationMillis());
            s.step = Step.BRAND;
        }
    };

    public void processStep(UserSession s, long chatId, String text, MessageService messageService) throws IOException, InterruptedException {
        messageService.sendText(chatId, "Step " + this.name() + " doesn't have any further processing.");
    }
}
