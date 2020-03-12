package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public void onDataSelected(Calendar firstDate, Calendar secondDate) {
        Date date1 = null;
        Date date2 = null;

        PieChartSettings.pieChartPrimarySettings(pieChart);
        PieChartSettings.pieChartLegendSettings(pieChart);

        if (firstDate != null) {
            String stringFirstDay = simpleDate.format(firstDate.getTime()).trim();
            textViewDate.setText(stringFirstDay);
            try {
                date1 = simpleDate.parse(stringFirstDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (secondDate != null) {
                String stringSecondDay = simpleDate.format(secondDate.getTime()).trim();
                textViewDate.append( " - " + stringSecondDay);
                try {
                    date2 = simpleDate.parse(stringSecondDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                date2 = date1;
            }
        }

        List<EmotionForHistory> emotionItems = db.emotionForHistoryDao().getEmotions(date1.getTime(), date2.getTime());

        for (int i = 0; i < items.size(); i++) {
            float valuePlus = 0;
            float valueMinus = 0;
            for (int j = 0; j < emotionItems.size(); j++) {
                int emotionLevel = Integer.parseInt(emotionItems.get(j).getEmotionLevel().substring(0, emotionItems.get(j).getEmotionLevel().length() - 2));
                if (items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) && emotionLevel > 0) {
                    valuePlus = valuePlus + emotionLevel;
                }
                else if (items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) && emotionLevel < 0){
                    valueMinus = valueMinus + emotionLevel;
                }
            }
            if (valuePlus > 0) {
                values.add(new PieEntry(valuePlus, "+ " + items.get(i).getEmotionName()));
            }
            if (valueMinus < 0){
                values.add(new PieEntry(valueMinus * (-1), "- " + items.get(i).getEmotionName()));
            }
        }
        PieChartSettings.pieChartSecondSettings(pieChart, values);
    }
}
