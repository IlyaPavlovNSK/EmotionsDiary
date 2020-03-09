package com.pavlovnsk.emotionsdiary.Data;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private SimpleDateFormat fullDate = Utils.DATE_FOR_ROOM;

    @TypeConverter
    public Date StringInDate(String stringDate){
        Date date = null;
        try {
            date = fullDate.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @TypeConverter
    public String DateInString(Date date){
        String stringDate = fullDate.format(date);
        return stringDate;
    }
}
