package com.formento.cadastro.model.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.formento.cadastro.util.LocalDateUtil;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalDateDeserializerTest {

    @Test
    public void deveFazerDeserializer() throws IOException {
        // given
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String date = "29-08-2016";

        // when
        when(jsonParser.getText()).thenReturn(date);
        LocalDate deserialize = localDateDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertEquals(LocalDateUtil.fromText(date), deserialize);
    }

    @Test
    public void deveFazerDeserializerSemObjeto() throws IOException {
        // given
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String date = null;

        // when
        when(jsonParser.getText()).thenReturn(date);
        LocalDate deserialize = localDateDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertNull(deserialize);
    }

    @Test
    public void deveFazerDeserializerComObjetoEmBranco() throws IOException {
        // given
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        DeserializationContext deserializationContext = mock(DeserializationContext.class);
        String date = "";

        // when
        when(jsonParser.getText()).thenReturn(date);
        LocalDate deserialize = localDateDeserializer.deserialize(jsonParser, deserializationContext);

        // then
        assertNull(deserialize);
    }

}
