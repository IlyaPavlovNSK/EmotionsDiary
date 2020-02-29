package com.pavlovnsk.emotionsdiary.Fragments;

import android.view.View;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import dagger.Provides;

public interface ListEmotionsItemView {
    void showSnackBar(EmotionItem item, int position);
}
