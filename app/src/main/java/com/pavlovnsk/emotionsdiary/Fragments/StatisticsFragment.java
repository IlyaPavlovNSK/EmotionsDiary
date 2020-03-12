package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;
import com.pavlovnsk.emotionsdiary.R;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.DaggerStatisticFragmentComponent;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.DataSettings;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.StatisticFragmentComponent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class StatisticsFragment extends Fragment {

    private PieChart pieChart;
    private TextView textViewDate;
    @Inject AppDataBase6 db;
    private List<EmotionForItem> emotionItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StatisticFragmentComponent statisticFragmentComponent = DaggerStatisticFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        statisticFragmentComponent.inject(this);
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        emotionItems = db.emotionForItemDao().getEmotionsItem();
        pieChart = view.findViewById(R.id.pie_chart);
        FloatingActionButton btnData = view.findViewById(R.id.btn_data);
        textViewDate = view.findViewById(R.id.text_View_Date);

        btnData.setOnClickListener(listener);

        pieChart.setNoDataText("выберите даты");
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
            Date now = new Date();
            if (firstDate != null && firstDate.getTime().after(now)||
                    secondDate != null && secondDate.getTime().after(now)) {
                Toast.makeText(getContext(), "Вы выбрали ненаступившую дату", Toast.LENGTH_SHORT).show();
            }
            DataSettings dataSettings = new DataSettings(pieChart, textViewDate, emotionItems, db);
            dataSettings.onDataSelected(firstDate, secondDate);
        }
    };
}