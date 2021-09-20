package com.tempo2.application.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeParserUtil {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private DateTimeParserUtil() {
    }

    public static String getLocalDateTimeAsLegacyString(LocalDateTime localDateTime) {
        return dateTimeFormatter.format(localDateTime);
    }

}
