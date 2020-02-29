package com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain;

import android.graphics.Bitmap;

public interface EmotionRowView {

    void setEmotionLevel(String level);
    void setEmotionName(String name);
    void setDescription(String description);
    void setPictureBitmap(Bitmap bitmap);
}
