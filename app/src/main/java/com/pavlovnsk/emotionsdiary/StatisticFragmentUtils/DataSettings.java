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

public class DataSettings {

    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
    private Date date1;
    private Date date2;
    private PieChart pieChart;
    private TextView textViewDate;
    private Context context;

    public DataSettings(Context context, SimpleDateFormat formatForDateNow, Date date1, Date date2, PieChart pieChart, TextView textViewDate) {
        this.formatForDateNow = formatForDateNow;
        this.date1 = date1;
        this.date2 = date2;
        this.pieChart = pieChart;
        this.textViewDate = textViewDate;
        this.context = context;
    }

    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null) {
            date1 = firstDate.getTime();
            String f = formatForDateNow.format(date1);
            textViewDate.setText(f);
            if (secondDate != null) {
                date2 = secondDate.getTime();
                String s = " - " + formatForDateNow.format(date2);
                textViewDate.append(s);
            }
            else {
                date2 = firstDate.getTime();
            }
        }

        ArrayList<PieEntry> values = new ArrayList<>();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        ArrayList<EmotionItem> emotionItems = dataBaseHelper.getEmotions(date1, date2);
        ArrayList<EmotionItem> items = ArrayEmotions.createEmotions();

        for (int i = 0; i <items.size() ; i++) {
            float valuePlus = 0;
            for (int j = 0; j <emotionItems.size() ; j++) {
                if (items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) &&
                        Integer.parseInt(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2))>0){
                    valuePlus = valuePlus + Float.parseFloat(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2));
                }
            }
            if(valuePlus!=0){
                values.add(new PieEntry(valuePlus, "+ " + items.get(i).getEmotionName()));
            }
        }

        for (int i = 0; i <items.size() ; i++) {
            float valueMinus = 0;
            for (int j = 0; j <emotionItems.size() ; j++) {
                if(items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) &&
                        Integer.parseInt(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2))<0){
                    valueMinus = valueMinus + Float.parseFloat(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2));
                }
            }
            if (valueMinus != 0) {
                values.add(new PieEntry(valueMinus*(-1), "- " + items.get(i).getEmotionName()));
            }
        }

        PieChartSettings.pieChartSecondSettings(pieChart, values);
    }
}
