package com.pavlovnsk.emotionsdiary;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import java.util.ArrayList;

//Double Checked Locking & volatile singleton
public class ArrayEmotions {

    private static volatile ArrayList<EmotionItem> emotionItems;

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

