package com.pavlovnsk.emotionsdiary.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.pavlovnsk.emotionsdiary.POJO.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.POJO.EmotionForItem;

@Database(entities = {EmotionForItem.class, EmotionForHistory.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract EmotionForHistoryDao emotionForHistoryDao();
    public abstract EmotionForItemDao emotionForItemDao();
}
