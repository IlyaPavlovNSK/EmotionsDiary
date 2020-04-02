package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DataSettings {

    private SimpleDateFormat simpleDate = Utils.SIMPLE_DATE;

    private PieChart pieChart;
    private TextView textViewDate;
    private long firstDate;
    private long secondDate;
    private List<EmotionForItem> emotionsItems;
    private ArrayList<PieEntry> values = new ArrayList<>();

    @Inject
    public DataSettings(PieChart pieChart, TextView textViewDate, List<EmotionForItem> emotionsItems, long firstDate, long secondDate) {
        this.pieChart = pieChart;
        this.textViewDate = textViewDate;
        this.emotionsItems = emotionsItems;
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    public void writeDate() {
        String stringFirstDay = simpleDate.format(firstDate);
        textViewDate.setText(stringFirstDay);

        if (secondDate != firstDate) {
            String stringSecondDay = simpleDate.format(secondDate);
            textViewDate.append(" - " + stringSecondDay);
        }
    }

    public void onDataSelected(List<EmotionForHistory> emotionsHistory) {
        PieChartSettings.pieChartPrimarySettings(pieChart);
        PieChartSettings.pieChartLegendSettings(pieChart);

        for (int i = 0; i < emotionsItems.size(); i++) {
            Log.d("myTag", "emotionsItems.size - " + emotionsItems.size());
            Log.d("myTag", "emotionsHistory.size - " + emotionsHistory.size());

            float valuePlus = 0;
            float valueMinus = 0;
            for (int j = 0; j < emotionsHistory.size(); j++) {
                int emotionLevel = Integer.parseInt(emotionsHistory.get(j).getEmotionLevel().substring(0, emotionsHistory.get(j).getEmotionLevel().length() - 2));
                if (emotionsItems.get(i).getEmotionName().equals(emotionsHistory.get(j).getEmotionName()) && emotionLevel > 0) {
                    valuePlus = valuePlus + emotionLevel;
                } else if (emotionsItems.get(i).getEmotionName().equals(emotionsHistory.get(j).getEmotionName()) && emotionLevel < 0) {
                    valueMinus = valueMinus + emotionLevel;
                }
            }
            if (valuePlus > 0) {
                values.add(new PieEntry(valuePlus, "+ " + emotionsItems.get(i).getEmotionName()));
            }
            if (valueMinus < 0) {
                values.add(new PieEntry(valueMinus * (-1), "- " + emotionsItems.get(i).getEmotionName()));
            }
        }
        PieChartSettings.pieChartSecondSettings(pieChart, values);
        pieChart.notifyDataSetChanged();
    }
}
