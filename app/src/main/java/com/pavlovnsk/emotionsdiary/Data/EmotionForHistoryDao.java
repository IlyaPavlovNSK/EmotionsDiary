package com.pavlovnsk.emotionsdiary.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pavlovnsk.emotionsdiary.POJO.EmotionForHistory;

import java.util.List;

@Dao
public interface EmotionForHistoryDao {

    @Query("SELECT * FROM EmotionForHistory")
    List<EmotionForHistory> getAllEmotions();

    @Insert
    void addEmotion(EmotionForHistory item);

    @Query("SELECT * FROM EmotionForHistory WHERE date BETWEEN :date1 AND :date2")
    List<EmotionForHistory> getEmotions(long date1, long date2);


}
