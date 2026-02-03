package org.example.bot;

import org.example.scraper.CarBrand;
import org.example.scraper.CarDataScraper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public InlineKeyboardMarkup brands() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        List<CarBrand> carNames = CarDataScraper.scrapeBrands();

        for (CarBrand car : carNames) {
            rows.add(new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                            .text(car.name())
                            .callbackData("BRAND|" + car.id())
                            .build()
            ));
        }
        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup models(List<ModelOption> models) {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (ModelOption model : models) {
            rows.add(new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                            .text(model.title)
                            .callbackData("MODEL|" + model.id)
                            .build()
            ));
        }
        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup generations(List<GenerationOption> generations) {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (GenerationOption gen : generations) {
            rows.add(new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                            .text(gen.title)
                            .callbackData("GEN|" + gen.id)
                            .build()
            ));
        }
        return new InlineKeyboardMarkup(rows);
    }
}