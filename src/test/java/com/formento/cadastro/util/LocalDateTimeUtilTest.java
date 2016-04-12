package com.formento.cadastro.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LocalDateTimeUtilTest {

    @Test
    public void testFromText() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2016, 3, 31, 18,30,40);

        // when
        String format = LocalDateTimeUtil.format(localDateTime);

        // then
        assertEquals("31-03-2016 18:30:40", format);
    }

    @Test
    public void testFormat() {
        // given
        String text = "31-03-2016 18:30:40";

        // when
        LocalDateTime localDateTime = LocalDateTimeUtil.fromText(text);

        // then
        assertEquals(LocalDateTime.of(2016, 3, 31, 18,30,40), localDateTime);
    }

}
