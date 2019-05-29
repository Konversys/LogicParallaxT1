package com.example.myapplication.model.logic;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

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

    public static Date getFormattedDateWithoutTime(Long date){
        return new Date(date);
    }

    public static String getFormattedStringDateDots(Date date){
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public static String getFormattedStringDateLine(Long dateTime) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateTime));
    }

    public static LocalTime getDifferenceOfStationDates(Date date, Date subject){
        Duration duration = new Duration(date.getTime(), subject.getTime());
        LocalTime result = new LocalTime(duration.getMillis());
        return result;
    }

    public static String getStringDifferenceOfStationDates(LocalTime time){
        String result = "";
        if (time.getHourOfDay() != 0)
            result += time.getHourOfDay() + "ч. ";
        if (time.getHourOfDay() != 0 && time.getMinuteOfHour() != 0)
            result += time.getMinuteOfHour() + "м. ";
        if (time.getHourOfDay() != 0 && time.getMinuteOfHour() != 0 && time.getSecondOfMinute() != 0)
            result += time.getSecondOfMinute() + "с.";
        return result;
    }
}
