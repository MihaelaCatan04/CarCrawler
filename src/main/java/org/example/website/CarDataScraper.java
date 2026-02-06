package org.example.website;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.common.HttpService;
import org.example.model.entity.CarBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarDataScraper {

    private static final Logger log = LoggerFactory.getLogger(CarDataScraper.class);

    private static final String URL = "https://999.md/ro/list/transport/cars?_rsc=r2aah";
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_ACCEPT_VALUE = "text/x-component";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String USER_AGENT_VALUE =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0";

    private static final String PAYLOAD_RECONSTRUCTOR =
            "self\\.__next_f\\.push\\(\\[\\d+,\"(.*?)\"\\]\\)";
    private static final String NAME_FILTER =
            "\"id\":1,\"type\":\"FILTER_TYPE_OPTIONS\"";

    private static final String JSON_FEATURES = "features";
    private static final String JSON_OPTIONS = "options";
    private static final String JSON_PRESENT_DYNAMIC = "presentInDynamicFilters";
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_TRANSLATED = "translated";

    private static final HttpService httpService = new HttpService();

    public static List<CarBrand> scrapeBrands() {

        List<CarBrand> brands = new ArrayList<>();

        try {
            Map<String, String> headers = new HashMap<>();
            headers.put(HEADER_ACCEPT, HEADER_ACCEPT_VALUE);
            headers.put(HEADER_USER_AGENT, USER_AGENT_VALUE);

            String body = httpService.get(URL, headers);

            String fullData = reconstructPayload(body);
            String brandFilterJson = isolateMarcaFilter(fullData);

            if (brandFilterJson != null) {
                brands = parseVisibleBrands(brandFilterJson);
            }

            log.info("Successfully scraped {} visible brands.", brands.size());

        } catch (IOException | InterruptedException e) {
            log.error("Scraping failed", e);
            Thread.currentThread().interrupt();
        }

        return brands;
    }

    private static String reconstructPayload(String body) {
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile(PAYLOAD_RECONSTRUCTOR);
        Matcher m = p.matcher(body);

        while (m.find()) {
            String chunk = m.group(1)
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
            sb.append(chunk);
        }
        return sb.toString();
    }

    private static String isolateMarcaFilter(String payload) {
        int index = payload.indexOf(NAME_FILTER);
        if (index == -1) return null;

        int startPos = payload.lastIndexOf("{", index);
        int braceCount = 0;
        boolean inString = false;

        for (int i = startPos; i < payload.length(); i++) {
            char c = payload.charAt(i);

            if (c == '"' && (i == 0 || payload.charAt(i - 1) != '\\')) {
                inString = !inString;
            }

            if (!inString) {
                if (c == '{') braceCount++;
                else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        return payload.substring(startPos, i + 1);
                    }
                }
            }
        }
        return null;
    }

    private static List<CarBrand> parseVisibleBrands(String json) {

        List<CarBrand> list = new ArrayList<>();

        try {
            JsonObject filterObj = JsonParser.parseString(json).getAsJsonObject();
            JsonArray features = filterObj.getAsJsonArray(JSON_FEATURES);

            if (features != null && !features.isEmpty()) {

                JsonArray options = features.get(0)
                        .getAsJsonObject()
                        .getAsJsonArray(JSON_OPTIONS);

                if (options != null) {
                    for (JsonElement el : options) {
                        JsonObject opt = el.getAsJsonObject();

                        boolean isVisible =
                                opt.get(JSON_PRESENT_DYNAMIC).getAsBoolean();

                        if (isVisible) {
                            int id = opt.get(JSON_ID).getAsInt();

                            String name = opt
                                    .getAsJsonObject(JSON_TITLE)
                                    .get(JSON_TRANSLATED)
                                    .getAsString();

                            list.add(new CarBrand(name, id));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error parsing visible brands: {}", e.getMessage());
        }

        return list;
    }
}
