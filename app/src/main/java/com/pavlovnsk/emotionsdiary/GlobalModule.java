package com.pavlovnsk.emotionsdiary;

import android.content.Context;

import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.getEmotionsItem();
    }
}

