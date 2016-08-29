package com.formento.cadastro.model.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.formento.cadastro.util.LocalDateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return Optional
                .ofNullable(jsonParser.getText())
                .filter(text -> !text.isEmpty())
                .map(LocalDateTimeUtil::fromText)
                .orElse(null);
    }

}
