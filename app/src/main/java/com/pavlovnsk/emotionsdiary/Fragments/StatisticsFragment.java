package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.ArrayEmotions;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class StatisticsFragment extends Fragment {

    private PieChart pieChart;
    private FloatingActionButton btnData;
    private TextView textViewDate;
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
    private Date date1;
    private Date date2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pie_chart);
        btnData = view.findViewById(R.id.btn_data);
        textViewDate = view.findViewById(R.id.text_View_Date);

        btnData.setOnClickListener(listener);

        PieChartSettings.pieChartPrimarySettings(pieChart);
        PieChartSettings.pieChartLegenSettings(pieChart);
        return view;
    }

    private FloatingActionButton.OnClickListener listener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            assert getFragmentManager() != null;
            new SlyCalendarDialog().setSingle(false).setCallback(callback).show(getFragmentManager(), "TAG_SLYCALENDAR");
        }
    };


    private SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
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
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
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
    };
}
