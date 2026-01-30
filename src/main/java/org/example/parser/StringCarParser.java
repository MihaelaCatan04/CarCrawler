package org.example.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Advert;
import org.example.model.Root;

import java.util.ArrayList;
import java.util.List;

public class StringCarParser {
    public List<Advert> parse(String stringCars) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Root allDataNode = objectMapper.readValue(stringCars, Root.class);
        return new ArrayList<>(allDataNode.data().searchAds().ads());
    }
}
