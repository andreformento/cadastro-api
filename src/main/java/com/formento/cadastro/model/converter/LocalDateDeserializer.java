package com.formento.cadastro.model.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.formento.cadastro.util.LocalDateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return Optional
                .ofNullable(jsonParser.getText())
                .filter(text -> !text.isEmpty())
                .map(LocalDateUtil::fromText)
                .orElse(null);
    }

}