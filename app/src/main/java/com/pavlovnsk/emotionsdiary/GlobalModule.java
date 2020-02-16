package com.pavlovnsk.emotionsdiary;

import android.content.Context;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GlobalModule {

    private Context context;

    public GlobalModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context getSomeContext() {
        return this.context;
    }

    @Provides
    @Singleton
    ArrayList<EmotionItem> createEmotions() {
        ArrayList<EmotionItem> emotionItems = new ArrayList<>();

        emotionItems.add(new EmotionItem("радость", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("удивление", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("печаль", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("гнев", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("отвращение", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("презрение", "0 %", R.drawable.img_slide_6));
        emotionItems.add(new EmotionItem("страх", "0 %", R.drawable.img_slide_6));

        return emotionItems;
    }
}

