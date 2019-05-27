package com.example.myapplication.model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static Date getFormattedDateWithTime(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
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
}
