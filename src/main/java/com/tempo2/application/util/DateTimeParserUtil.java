package com.tempo2.application.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeParserUtil {

    private static final DateTimeFormatter POST_REQUEST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final DateTimeFormatter GET_REQUEST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeParserUtil() {
    }

    public static String getLocalDateTimeAsLegacyString(LocalDateTime localDateTime) {
        return POST_REQUEST_DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static String getLocalDateTimeAsRepresentationString(LocalDateTime localDateTime) {
        return GET_REQUEST_DATE_TIME_FORMATTER.format(localDateTime);
    }

}
