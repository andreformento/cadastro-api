package com.formento.cadastro.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class LocalDateUtilTest {

    @Test
    public void testFromText() {
        // given
        LocalDate localDate = LocalDate.of(2016, 3, 31);

        // when
        String format = LocalDateUtil.format(localDate);

        // then
        assertEquals("31-03-2016", format);
    }

    @Test
    public void testFormat() {
        // given
        String text = "31-03-2016";

        // when
        LocalDate localDate = LocalDateUtil.fromText(text);

        // then
        assertEquals(LocalDate.of(2016, 3, 31), localDate);
    }

}
