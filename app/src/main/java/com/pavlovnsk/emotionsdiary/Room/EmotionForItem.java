package com.pavlovnsk.emotionsdiary.Room;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EmotionForItem {

    @PrimaryKey (autoGenerate  =  true)
    private int emotionId;
    private String emotionName;
    private String emotionLevel;
    private String description;
    private long date;
    private String emotionPic;

    public EmotionForItem(String emotionName, String emotionLevel, String description, long date, String emotionPic) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
        this.description = description;
        this.date = date;
        this.emotionPic = emotionPic;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(int emotionId) {
        this.emotionId = emotionId;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public String getEmotionLevel() {
        return emotionLevel;
    }

    public void setEmotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getEmotionPic() {
        return emotionPic;
    }

    public void setEmotionPic(String emotionPic) {
        this.emotionPic = emotionPic;
    }
}
