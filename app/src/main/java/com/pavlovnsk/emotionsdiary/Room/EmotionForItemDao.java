package com.pavlovnsk.emotionsdiary.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmotionForItemDao {

    @Insert
    void addEmotionItem(EmotionForItem item);

    @Insert
    void addDefaultEmotionItem(List<EmotionForItem> items);

    @Query("SELECT * FROM EmotionForItem")
    List<EmotionForItem> getEmotionsItem();

    @Delete
    void deleteEmotionItem(EmotionForItem item);
}
