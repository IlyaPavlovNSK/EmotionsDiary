package com.pavlovnsk.emotionsdiary.POJO;

import javax.inject.Inject;

import dagger.Module;


public class EmotionItem {

    private String emotionName;
    private String emotionLevel;
    private int emotionPic;


    public EmotionItem(String emotionName, String emotionLevel, int emotionPic) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
        this.emotionPic = emotionPic;
    }

    public EmotionItem(String emotionName, String emotionLevel) {
        this.emotionName = emotionName;
        this.emotionLevel = emotionLevel;
    }


    public EmotionItem() {
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

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    public void setEmotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public void setEmotionPic(int emotionPic) {
        this.emotionPic = emotionPic;
    }
}
