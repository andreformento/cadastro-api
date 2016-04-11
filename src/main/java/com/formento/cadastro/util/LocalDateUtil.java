package com.formento.cadastro.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateUtil {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public static LocalDate fromText(String text) {
        return LocalDate.parse(text, FORMATTER);
    }

    public static String format(LocalDate localDate) {
        return localDate.format(FORMATTER);
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
