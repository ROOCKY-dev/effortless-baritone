/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharsWrapper;
import dev.huskuraft.effortless.api.nightconfig.core.io.ParsingException;
import dev.huskuraft.effortless.api.nightconfig.core.io.Utils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;

final class TemporalParser {
    private static final char[] ALLOWED_DT_SEPARATORS = new char[]{'T', 't', ' '};
    private static final char[] OFFSET_INDICATORS = new char[]{'Z', '+', '-'};

    static Temporal parse(CharsWrapper chars) {
        if (chars.get(2) == ':') {
            return TemporalParser.parseTime(chars);
        }
        LocalDate date = TemporalParser.parseDate(chars);
        if (chars.length() == 10) {
            return date;
        }
        char dateTimeSeparator = chars.get(10);
        if (!Utils.arrayContains(ALLOWED_DT_SEPARATORS, dateTimeSeparator)) {
            throw new ParsingException("Invalid separator between date and time: '" + dateTimeSeparator + "'.");
        }
        CharsWrapper afterDate = chars.subView(11);
        int offsetIndicatorIndex = afterDate.indexOfFirst(OFFSET_INDICATORS);
        if (offsetIndicatorIndex == -1) {
            LocalTime time = TemporalParser.parseTime(afterDate);
            return LocalDateTime.of(date, time);
        }
        LocalTime time = TemporalParser.parseTime(afterDate.subView(0, offsetIndicatorIndex));
        ZoneOffset offset = ZoneOffset.of(afterDate.subView(offsetIndicatorIndex).trimmedView().toString());
        return OffsetDateTime.of(date, time, offset);
    }

    private static LocalDate parseDate(CharsWrapper chars) {
        CharsWrapper yearChars = chars.subView(0, 4);
        CharsWrapper monthChars = chars.subView(5, 7);
        CharsWrapper dayChars = chars.subView(8, 10);
        int year = Utils.parseInt(yearChars, 10);
        int month = Utils.parseInt(monthChars, 10);
        int day = Utils.parseInt(dayChars, 10);
        return LocalDate.of(year, month, day);
    }

    private static LocalTime parseTime(CharsWrapper chars) {
        int nanos;
        CharsWrapper hourChars = chars.subView(0, 2);
        CharsWrapper minuteChars = chars.subView(3, 5);
        CharsWrapper secondChars = chars.subView(6, 8);
        int hour = Utils.parseInt(hourChars, 10);
        int minutes = Utils.parseInt(minuteChars, 10);
        int seconds = Utils.parseInt(secondChars, 10);
        if (chars.length() > 8) {
            CharsWrapper fractionChars = new CharsWrapper(chars.subView(9));
            if (fractionChars.length() > 9) {
                fractionChars = fractionChars.subView(0, 9);
            }
            int value = Utils.parseInt(fractionChars, 10);
            int coeff = (int)Math.pow(10.0, 9 - fractionChars.length());
            nanos = value * coeff;
        } else {
            nanos = 0;
        }
        return LocalTime.of(hour, minutes, seconds, nanos);
    }

    private TemporalParser() {
    }
}
