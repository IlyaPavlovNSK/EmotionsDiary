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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pavlovnsk.emotionsdiary.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class StatisticsFragment extends Fragment {

    private PieChart pieChart;
    private Button btnData;
    private TextView before;
    private TextView after;
    private Calendar calendar = Calendar.getInstance();
    private SlyCalendarDialog dialog = new SlyCalendarDialog();
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pie_chart);
        btnData = view.findViewById(R.id.btn_data);
        before = view.findViewById(R.id.tv_before);
        after = view.findViewById(R.id.tv_after);

        btnData.setOnClickListener(listener);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> values = new ArrayList<>();

        values.add(new PieEntry(40, "emo1"));
        values.add(new PieEntry(40, "emo2"));

        PieDataSet pieDataSet = new PieDataSet(values, "Emotions");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

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
                Date dateBefore = firstDate.getTime();
                String f = formatForDateNow.format(dateBefore);
                after.setText(f);
                if (secondDate != null) {
                    Date dateAfter = secondDate.getTime();
                    String s = formatForDateNow.format(dateAfter);
                    before.setText(s);
                }
            }
        }
    };
}
