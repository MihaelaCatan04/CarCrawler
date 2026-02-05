package org.example.bot;

import org.example.scraper.CarBrand;
import org.example.scraper.CarDataScraper;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class KeyboardFactory {

    public InlineKeyboardMarkup brands() {
        List<CarBrand> carNames = CarDataScraper.scrapeBrands();
        return new InlineKeyboardMarkup(RowsTemplate(carNames, CarBrand::getName, car -> "BRAND|" + car.getId()));
    }

    public InlineKeyboardMarkup models(List<ModelOption> models) {
        return new InlineKeyboardMarkup(RowsTemplate(models, ModelOption::getTitle, model -> "MODEL|" + model.getId()));
    }

    public InlineKeyboardMarkup generations(List<GenerationOption> generations) {
        return new InlineKeyboardMarkup(RowsTemplate(generations, GenerationOption::getTitle, gen -> "GEN|" + gen.getId()));
    }

    private <T> List<InlineKeyboardRow> RowsTemplate(List<T> items, Function<T, String> textMapper, Function<T, String> callbackMapper) {

        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (T item : items) {
            rows.add(new InlineKeyboardRow(InlineKeyboardButton.builder().text(textMapper.apply(item)).callbackData(callbackMapper.apply(item)).build()));
        }
        return rows;
    }
}
