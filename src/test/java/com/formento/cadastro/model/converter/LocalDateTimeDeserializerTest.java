package com.formento.cadastro.model.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.formento.cadastro.util.LocalDateTimeUtil;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalDateTimeDeserializerTest {

    @Test
    public void deveFazerDeserializer() throws IOException {
        // given
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String time = "29-08-2016 19:54:35";

        // when
        when(jsonParser.getText()).thenReturn(time);
        LocalDateTime deserialize = localDateTimeDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertEquals(LocalDateTimeUtil.fromText(time), deserialize);
    }

    @Test
    public void deveFazerDeserializerSemObjeto() throws IOException {
        // given
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String time = null;

        // when
        when(jsonParser.getText()).thenReturn(time);
        LocalDateTime deserialize = localDateTimeDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertNull(deserialize);
    }

    @Test
    public void deveFazerDeserializerComObjetoEmBranco() throws IOException {
        // given
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String time = "";

        // when
        when(jsonParser.getText()).thenReturn(time);
        LocalDateTime deserialize = localDateTimeDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertNull(deserialize);
    }

}
