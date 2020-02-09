package com.pavlovnsk.emotionsdiary.POJO;

public class EmotionItem {

    private String emotionName;
    private String emotionLevel;
    private int emotionPic;

    public EmotionItem(String emotionName, String aemotionLevel, int emotionPic) {
        this.emotionName = emotionName;
        this.emotionLevel = aemotionLevel;
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

    public void setAemotionLevel(String emotionLevel) {
        this.emotionLevel = emotionLevel;
    }

    public void setEmotionPic(int emotionPic) {
        this.emotionPic = emotionPic;
    }
}
