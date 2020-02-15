package com.pavlovnsk.emotionsdiary;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

//Double Checked Locking & volatile singleton
@Module
public class ArrayEmotions {

    private static volatile ArrayList<EmotionItem> emotionItems;

    @Provides
    public static ArrayList<EmotionItem> createEmotions(){
        ArrayList<EmotionItem> arrayEmotions = emotionItems;
        if (arrayEmotions==null){
            synchronized (ArrayEmotions.class){
                arrayEmotions = emotionItems;
                if(arrayEmotions == null){
                    emotionItems = arrayEmotions = new ArrayList<>();

                    emotionItems.add(new EmotionItem("радость", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("удивление", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("печаль", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("гнев", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("отвращение", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("презрение", "0 %", R.drawable.img_slide_6));
                    emotionItems.add(new EmotionItem("страх", "0 %", R.drawable.img_slide_6));
                }
            }
        }
        return arrayEmotions;
    }
}

