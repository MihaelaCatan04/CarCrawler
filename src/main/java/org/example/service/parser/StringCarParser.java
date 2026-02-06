package org.example.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.response.Root;
import org.example.model.entity.Advert;

import java.util.ArrayList;
import java.util.List;

public class StringCarParser {
    public List<Advert> parse(String stringCars) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Root allDataNode = objectMapper.readValue(stringCars, Root.class);
        return new ArrayList<>(allDataNode.data().searchAds().ads());
    }
}
