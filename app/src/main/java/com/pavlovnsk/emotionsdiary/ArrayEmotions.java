package com.pavlovnsk.emotionsdiary;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

public class ArrayEmotions {

    public static ArrayList<EmotionItem> createEmotions(){
        ArrayList<EmotionItem> emotionItems = new ArrayList<>();

        emotionItems.add(new EmotionItem("радость", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("удивление", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("печаль", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("гнев", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("отвращение", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("презрение", "0 %", R.drawable.redwood));
        emotionItems.add(new EmotionItem("страх", "0 %", R.drawable.redwood));

        return emotionItems;
    }
}
