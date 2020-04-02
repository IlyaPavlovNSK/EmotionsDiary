package com.pavlovnsk.emotionsdiary.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface EmotionForItemDao {

    @Insert
    void addEmotionItem(EmotionForItem item);

    @Query("SELECT * FROM EmotionForItem")
    Flowable<List<EmotionForItem>> getEmotionsItem();

    @Delete
    void deleteEmotionItem(EmotionForItem item);
}
