package com.pavlovnsk.emotionsdiary.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EmotionForItem.class, EmotionForHistory.class}, version = 1, exportSchema = false)
public abstract class AppRoomDataBase extends RoomDatabase {

    public abstract EmotionForHistoryDao emotionForHistoryDao();
    public abstract EmotionForItemDao emotionForItemDao();
}
