/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.toml;

import dev.huskuraft.effortless.api.nightconfig.core.io.CharacterOutput;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

final class TemporalWriter {
    static void write(Temporal temporal, CharacterOutput output) {
        if (temporal.isSupported(ChronoField.YEAR)) {
            TemporalWriter.writeDate(temporal, output);
            if (temporal.isSupported(ChronoField.HOUR_OF_DAY)) {
                output.write('T');
                TemporalWriter.writeHour(temporal, output);
                if (temporal.isSupported(ChronoField.OFFSET_SECONDS)) {
                    int offsetSeconds = temporal.get(ChronoField.OFFSET_SECONDS);
                    ZoneOffset offset = ZoneOffset.ofTotalSeconds(offsetSeconds);
                    output.write(offset.getId());
                }
            }
        } else if (temporal.isSupported(ChronoField.HOUR_OF_DAY)) {
            TemporalWriter.writeHour(temporal, output);
        }
    }

    private static void writeDate(Temporal temporal, CharacterOutput output) {
        int year = temporal.get(ChronoField.YEAR);
        int month = temporal.get(ChronoField.MONTH_OF_YEAR);
        int day = temporal.get(ChronoField.DAY_OF_MONTH);
        TemporalWriter.writePadded(year, 4, output);
        output.write('-');
        TemporalWriter.writePadded(month, 2, output);
        output.write('-');
        TemporalWriter.writePadded(day, 2, output);
    }

    private static void writeHour(Temporal temporal, CharacterOutput output) {
        int millis;
        int hours = temporal.get(ChronoField.HOUR_OF_DAY);
        int minutes = temporal.get(ChronoField.MINUTE_OF_HOUR);
        int seconds = temporal.get(ChronoField.SECOND_OF_MINUTE);
        TemporalWriter.writePadded(hours, 2, output);
        output.write(':');
        TemporalWriter.writePadded(minutes, 2, output);
        output.write(':');
        TemporalWriter.writePadded(seconds, 2, output);
        if (temporal.isSupported(ChronoField.NANO_OF_SECOND)) {
            int nanos = temporal.get(ChronoField.NANO_OF_SECOND);
            if (nanos != 0) {
                output.write('.');
                TemporalWriter.writePaddedAndTrimmed(nanos, 9, output);
            }
        } else if (temporal.isSupported(ChronoField.MILLI_OF_SECOND) && (millis = temporal.get(ChronoField.MILLI_OF_SECOND)) != 0) {
            output.write('.');
            TemporalWriter.writePaddedAndTrimmed(millis, 6, output);
        }
    }

    private static void writePadded(int value, int numberOfDigits, CharacterOutput output) {
        String str = Integer.toString(value);
        for (int i = str.length(); i < numberOfDigits; ++i) {
            output.write('0');
        }
        output.write(str);
    }

    private static void writePaddedAndTrimmed(int value, int numberOfDigits, CharacterOutput output) {
        int length;
        int i;
        String str = Integer.toString(value);
        for (i = length = str.length(); i < numberOfDigits; ++i) {
            output.write('0');
        }
        for (i = length - 1; i >= 1; --i) {
            if (str.charAt(i) != '0') continue;
            --length;
        }
        output.write(str, 0, length);
    }

    private TemporalWriter() {
    }
}
