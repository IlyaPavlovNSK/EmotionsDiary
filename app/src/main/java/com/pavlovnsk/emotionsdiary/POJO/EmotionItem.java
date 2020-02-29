package com.pavlovnsk.emotionsdiary.POJO;

import android.graphics.Bitmap;

import javax.inject.Inject;

public class EmotionItem {

    private int emotionId;
    private String emotionName;
    private String emotionLevel;
    private String description;
    private Bitmap emotionPic;
    private String date;

    public EmotionItem(int emotionId, String emotionName, String emotionLevel, String description, Bitmap emotionPic) {
        this.emotionId = emotionId;
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
        this.description = description;
        this.emotionPic = emotionPic;
    }


    public EmotionItem(String emotionName, String description, String date) {
        this.emotionName = emotionName;
        this.description = description;
        this.date = date;
    }


    public EmotionItem(String emotionName, String emotionLevel, String description, Bitmap emotionPic) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
        this.description = description;
        this.emotionPic = emotionPic;
    }


    public EmotionItem(String emotionName, String emotionLevel) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
    }

    public EmotionItem() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmotionId() {
        return emotionId;
    }

    public void setEmotionId(int id) {
        this.emotionId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public String getEmotionLevel() {
        return emotionLevel;
    }

    public Bitmap getEmotionPic() {
        return emotionPic;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public void setEmotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public void setEmotionPic(Bitmap emotionPic) {
        this.emotionPic = emotionPic;
    }
}
