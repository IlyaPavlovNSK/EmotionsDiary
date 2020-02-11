package com.pavlovnsk.emotionsdiary.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
    private Button btnData;
    private TextView textViewDate2;
    private TextView textViewDate1;
    private Calendar calendar = Calendar.getInstance();
    private SlyCalendarDialog dialog = new SlyCalendarDialog();
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
    private Date date1;
    private Date date2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pie_chart);
        btnData = view.findViewById(R.id.btn_data);
        textViewDate1 = view.findViewById(R.id.text_View_Date1);
        textViewDate2 = view.findViewById(R.id.text_View_Date2);

        btnData.setOnClickListener(listener);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 5, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        //цвет внутреннего круга
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateXY(3000, 3000);
        //диаметр прозрачного круга
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setMaxAngle(360f);
        //отображет текст при пустой диаграмме
        pieChart.setNoDataText("выберите даты");



        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setMaxSizePercent(0.5f);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);



        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new SlyCalendarDialog().setSingle(false).setCallback(callback).show(getFragmentManager(), "TAG_SLYCALENDAR");
        }
    };


    SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
        @Override
        public void onCancelled() {
        }

        @Override
        public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
            if (firstDate != null) {
                date1 = firstDate.getTime();
                String f = formatForDateNow.format(date1);
                textViewDate1.setText(f);
                if (secondDate != null) {
                    date2 = secondDate.getTime();
                    String s = formatForDateNow.format(date2);
                    textViewDate2.setText(s);
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
                values.add(new PieEntry(valuePlus, "+ " + items.get(i).getEmotionName()));
            }

            for (int i = 0; i <items.size() ; i++) {
                float valueMinus = 0;
                for (int j = 0; j <emotionItems.size() ; j++) {
                    if(items.get(i).getEmotionName().equals(emotionItems.get(j).getEmotionName()) &&
                            Integer.parseInt(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2))<0){
                        valueMinus = valueMinus + Float.parseFloat(emotionItems.get(j).getEmotionLevel().substring(0,emotionItems.get(j).getEmotionLevel().length()-2));
                    }
                }
                values.add(new PieEntry(valueMinus*(-1), "- " + items.get(i).getEmotionName()));
            }


            PieDataSet pieDataSet = new PieDataSet(values, null);

            pieDataSet.setSliceSpace(3f);
            pieDataSet.setSelectionShift(5f);
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            PieData data = new PieData(pieDataSet);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.WHITE);

            pieChart.setData(data);
            pieChart.invalidate();
        }
    };
}
