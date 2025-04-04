package io.ten1010.gpumetricmonitor.common.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static long convertStartTimestamp(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.atStartOfDay(ZoneId.of("Asia/Seoul")).toEpochSecond();
    }

    public static long convertEndTimestamp(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return date.plusDays(1)
                .atStartOfDay(ZoneId.of("Asia/Seoul"))
                .minusSeconds(1)
                .toEpochSecond();
    }
}
