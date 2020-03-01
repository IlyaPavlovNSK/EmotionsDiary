package com.pavlovnsk.emotionsdiary.Data;

import java.text.SimpleDateFormat;

public class Utils {

    static final int DATABASE_VERSION_HISTORY = 27;
    static final String DATABASE_NAME_HISTORY = "History_DB";

    static final String TABLE_NAME_HISTORY = "Your_emotions_history";
    static final String TABLE_NAME_ITEM = "Your_emotions_item";

    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_LEVEL = "level";
    static final String KEY_DATE = "date";

    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PIC = "pic";

    public static final SimpleDateFormat FULL_DATE = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE = new SimpleDateFormat("dd.MM.yyyy");
}
