package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class DataSettings {

    private SimpleDateFormat simpleDate = Utils.SIMPLE_DATE;

    private PieChart pieChart;
    private TextView textViewDate;
    private ArrayList<PieEntry> values = new ArrayList<>();
    private List<EmotionForItem> items;
    private AppDataBase6 db;

    @Inject
    public DataSettings(PieChart pieChart, TextView textViewDate, List<EmotionForItem> items, AppDataBase6 db) {
        this.pieChart = pieChart;
        this.textViewDate = textViewDate;
        this.items = items;
        this.db = db;
    }

    public void onDataSelected(long firstDate, long secondDate) {

        PieChartSettings.pieChartPrimarySettings(pieChart);
        PieChartSettings.pieChartLegendSettings(pieChart);

        String stringFirstDay = simpleDate.format(firstDate);
        textViewDate.setText(stringFirstDay);

        if (secondDate != firstDate) {
            String stringSecondDay = simpleDate.format(secondDate);
            textViewDate.append(" - " + stringSecondDay);
        }

        List<EmotionForHistory> emotionItems = db.emotionForHistoryDao().getEmotions(firstDate, secondDate + TimeUnit.DAYS.toMillis(1));

        for (int i = 0; i < items.size(); i++) {
            float valuePlus = 0;
            float valueMinus = 0;
            for (int j = 0; j < emotionItems.size(); j++) {
                int emotionLevel = Integer.parseInt(emotionItems.get(j).getEmotionLevel().substring(0, emotionItems.get(j).getEmotionLevel().length() - 2));
                if (items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) && emotionLevel > 0) {
                    valuePlus = valuePlus + emotionLevel;
                } else if (items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) && emotionLevel < 0) {
                    valueMinus = valueMinus + emotionLevel;
                }
            }
            if (valuePlus > 0) {
                values.add(new PieEntry(valuePlus, "+ " + items.get(i).getEmotionName()));
            }
            if (valueMinus < 0) {
                values.add(new PieEntry(valueMinus * (-1), "- " + items.get(i).getEmotionName()));
            }
        }
        PieChartSettings.pieChartSecondSettings(pieChart, values);
    }
}
