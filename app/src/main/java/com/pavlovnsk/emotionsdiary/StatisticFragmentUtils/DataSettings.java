package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class DataSettings {

    private SimpleDateFormat fullDate = Utils.FULL_DATE;
    private SimpleDateFormat simpleDate = Utils.SIMPLE_DATE;

    private PieChart pieChart;
    private TextView textViewDate;

    private ArrayList<PieEntry> values = new ArrayList<>();

    private ArrayList<EmotionItem> items;
    private DataBaseHelper dataBaseHelper;

    @Inject
    public DataSettings(PieChart pieChart, TextView textViewDate, ArrayList<EmotionItem> items, DataBaseHelper dataBaseHelper) {
        this.pieChart = pieChart;
        this.textViewDate = textViewDate;
        this.items = items;
        this.dataBaseHelper = dataBaseHelper;
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
