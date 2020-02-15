package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.pavlovnsk.emotionsdiary.ArrayEmotions;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class DataSettings {

    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
    private Date date1;
    private Date date2;
    private PieChart pieChart;
    private TextView textViewDate;

    private ArrayList<PieEntry> values = new ArrayList<>();
    private ArrayList<EmotionItem> items;
    private DataBaseHelper dataBaseHelper;

    @Inject
    public DataSettings(Context context, PieChart pieChart, TextView textViewDate) {
        this.pieChart = pieChart;
        this.textViewDate = textViewDate;

        dataBaseHelper = new DataBaseHelper(context);
        items = ArrayEmotions.createEmotions();
    }

    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

        PieChartSettings.pieChartPrimarySettings(pieChart);
        PieChartSettings.pieChartLegendSettings(pieChart);

        if (firstDate != null) {
            date1 = firstDate.getTime();
            String f = formatForDateNow.format(date1);
            textViewDate.setText(f);
            if (secondDate != null) {
                date2 = secondDate.getTime();
                String s = " - " + formatForDateNow.format(date2);
                textViewDate.append(s);
            } else {
                date2 = firstDate.getTime();
            }
        }

        ArrayList<EmotionItem> emotionItems = dataBaseHelper.getEmotions(date1, date2);

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
