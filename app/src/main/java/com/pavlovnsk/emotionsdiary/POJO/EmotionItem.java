package com.pavlovnsk.emotionsdiary.POJO;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class EmotionItem {

    private String emotionName;
    private String emotionLevel;
    private int emotionPic;
    private Bitmap bitmap;

    public EmotionItem(String emotionName, String aemotionLevel, int emotionPic) {
        this.emotionName = emotionName;
        this.emotionLevel = aemotionLevel;
        this.emotionPic = emotionPic;
        this.bitmap = BitmapFactory.decodeResource(Resources.getSystem(), emotionPic);
    }

    public String getEmotionName() {
        return emotionName;
    }

    public String getEmotionLevel() {
        return emotionLevel;
    }

    public int getEmotionPic() {
        return emotionPic;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public void setAemotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public void setEmotionPic(int emotionPic) {
        this.emotionPic = emotionPic;
    }
}
