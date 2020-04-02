package com.pavlovnsk.emotionsdiary.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface EmotionForHistoryDao {

    @Query("SELECT * FROM EmotionForHistory")
    Flowable<List<EmotionForHistory>> getAllEmotions();

    @Insert
    void addEmotion(EmotionForHistory item);

    @Query("SELECT * FROM EmotionForHistory WHERE date BETWEEN :date1 AND :date2")
    Flowable<List<EmotionForHistory>> getEmotions(long date1, long date2);


}
