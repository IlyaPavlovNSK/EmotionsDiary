package com.pavlovnsk.emotionsdiary.Fragments;

import android.content.Context;
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
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.DataSettings;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.PieChartSettings;

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
        PieChartSettings.pieChartLegendSettings(pieChart);
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
            DataSettings dataSettings = new DataSettings(getContext(), formatForDateNow, date1, date2, pieChart, textViewDate);
            dataSettings.onDataSelected(firstDate, secondDate, hours, minutes);
        }
    };
}