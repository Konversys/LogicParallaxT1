package com.example.myapplication.model.logic;

import org.joda.time.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static Date getFormattedDateWithTime(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    public static String getFormattedStringWithTime(Date date) {
        return new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(date);
    }

    public static String getFormattedStringDateLine(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date getFormattedDateWithoutTime(Long date) {
        return new Date(date);
    }

    public static String getFormattedStringDateDots(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String getFormattedStringDateLine(Long dateTime) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateTime));
    }

    public static Duration getDifferenceOfStationDates(Date date, Date subject) {
        if (date == null || subject == null)
            return null;
        return new Duration(date.getTime(), subject.getTime());
    }

    public static String getStringDifferenceOfStationDates(Duration time) {
        if (time == null)
            return "Неизвестно";
        StringBuilder result = new StringBuilder();
        long sec = time.getStandardSeconds();
        long days = sec / 60 / 60 / 24;
        long hours = sec / 60 / 60 - days * 24;
        long minutes = sec / 60 - days * 24 * 60 - hours * 60;
        long seconds = sec - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60;

        if (days != 0)
            result.append(String.format("%02dд. ", days));
        if (days != 0 || hours != 0)
            result.append(String.format("%02dч. ", hours));
        result.append(String.format("%02dм. ", minutes));
        result.append(String.format("%02dс. ", seconds));
        return result.toString();
    }
}
