package com.pavlovnsk.emotionsdiary;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.util.ArrayList;

public class ArrayEmotions {

    public static ArrayList<EmotionItem> createEmotions(){
        ArrayList<EmotionItem> emotionItems = new ArrayList<>();

        emotionItems.add(new EmotionItem("name1", "level1", R.drawable.ic_emotion));
        emotionItems.add(new EmotionItem("name2", "level2", R.drawable.ic_emotion));
        emotionItems.add(new EmotionItem("name3", "level3", R.drawable.ic_emotion));

        return emotionItems;
    }
}
