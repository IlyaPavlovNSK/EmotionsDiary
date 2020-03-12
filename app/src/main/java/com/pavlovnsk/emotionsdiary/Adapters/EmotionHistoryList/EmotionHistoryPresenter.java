package com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList;

import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;


public class EmotionHistoryPresenter {

    private List<EmotionForHistory> emotions;

    @Inject
    public EmotionHistoryPresenter(AppDataBase6 db) {
        this.emotions = db.emotionForHistoryDao().getAllEmotions();
    }

    void onBindEmotionsRowViewAtPosition(int position, EmotionHistoryRowView rowView) {
        EmotionForHistory emotionItem = emotions.get(position);
        Date date = new Date(emotionItem.getDate());
        String stringDate = Utils.FULL_DATE.format(date);
        rowView.setEmotionData(stringDate.substring(0,stringDate.length()-6).trim());
        rowView.setEmotionTime(stringDate.substring(10).trim());
        rowView.setEmotionName(emotionItem.getEmotionName());
        rowView.setDescription(emotionItem.getDescription());
    }

    int getEmotionsRowsCount() {
        return emotions.size();
    }
}
