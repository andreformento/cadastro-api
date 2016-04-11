package com.formento.cadastro.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    @Test
    public void testToDate() throws ParseException {
        // given
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse("14-03-2016");

        LocalDate localDate = LocalDate.of(2016, 3, 14);

        // when
        Date toDate = LocalDateUtil.toDate(localDate);

        // then
        assertEquals(date, toDate);
    }

}
