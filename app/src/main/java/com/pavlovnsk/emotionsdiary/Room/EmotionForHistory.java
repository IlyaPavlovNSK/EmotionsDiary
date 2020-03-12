package com.pavlovnsk.emotionsdiary.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EmotionForHistory {

    @PrimaryKey (autoGenerate  =  true)
    private int emotionId;
    private String emotionName;
    private String emotionLevel;
    private String description;
    private long date;

    public EmotionForHistory(String emotionName, String emotionLevel, String description, long date) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
        this.description = description;
        this.date = date;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public String getEmotionLevel() {
        return emotionLevel;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public void setEmotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
