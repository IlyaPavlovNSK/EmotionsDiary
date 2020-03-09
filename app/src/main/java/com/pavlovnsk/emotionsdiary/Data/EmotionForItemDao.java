package com.pavlovnsk.emotionsdiary.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pavlovnsk.emotionsdiary.POJO.EmotionForItem;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface EmotionForItemDao {

    @Insert
    void addEmotionItem(EmotionForItem item);

    @Insert
    void addDefaultEmotionItem(ArrayList<EmotionForItem> items);

    @Query("SELECT * FROM EmotionForItem")
    List<EmotionForItem> getEmotionsItem();

    @Delete
    void deleteEmotionItem(EmotionForItem item);
}
