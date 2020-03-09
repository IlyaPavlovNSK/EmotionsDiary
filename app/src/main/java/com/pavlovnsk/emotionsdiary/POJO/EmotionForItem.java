package com.pavlovnsk.emotionsdiary.POJO;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.pavlovnsk.emotionsdiary.Data.BitmapConverter;

@Entity
public class EmotionForItem extends EmotionForHistory {

    @TypeConverters({BitmapConverter.class})
    private Bitmap emotionPic;

    public EmotionForItem(String emotionName, String emotionLevel, String description, long date, Bitmap emotionPic) {
        super(emotionName, emotionLevel, description, date);
        this.emotionPic = emotionPic;
    }

    public Bitmap getEmotionPic() {
        return emotionPic;
    }

    public void setEmotionPic(Bitmap emotionPic) {
        this.emotionPic = emotionPic;
    }
}
