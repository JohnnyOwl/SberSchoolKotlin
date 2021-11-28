package ru.sber.datetime

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

fun getZonesWithNonDivisibleByHourOffset(): Set<String> {
    return ZoneId.getAvailableZoneIds()
        .filter {
            ZonedDateTime.now(ZoneId.of(it)).minute != ZonedDateTime.now(ZoneId.of("UTC")).minute
        }
        .toSet()
}

fun getLastInMonthDayWeekList(year: Int): List<String> {
    return Month.values().map { month ->
        LocalDateTime
            .of(year, month, 1, 1, 1)
            .with(TemporalAdjusters.lastDayOfMonth()).dayOfWeek.name
    }.toList()
}

fun getNumberOfFridayThirteensInYear(year: Int): Int {
    return Month.values().filter { month ->
        LocalDateTime.of(year, month, 13, 1, 1)
            .dayOfWeek == DayOfWeek.FRIDAY
    }.count()
}

fun getFormattedDateTime(dateTime: LocalDateTime): String {
    return DateTimeFormatter.ofPattern("dd MMM yyy, HH:mm", Locale.US).format(dateTime)
}
