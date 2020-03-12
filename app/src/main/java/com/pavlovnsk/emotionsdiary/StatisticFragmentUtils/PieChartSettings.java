package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

class PieChartSettings {

    static void pieChartPrimarySettings(PieChart pieChart){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 5, 10, 20);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        //цвет внутреннего круга
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateXY(700, 700);
        //диаметр прозрачного круга
        pieChart.setTransparentCircleRadius(55f);
        //отображет текст при пустой диаграмме
        pieChart.setDrawSliceText(false);
    }

    static void pieChartLegendSettings(PieChart pieChart){
        Legend legend = pieChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setMaxSizePercent(0.5f);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
    }

    static void pieChartSecondSettings(PieChart pieChart, ArrayList<PieEntry> values){
        PieDataSet pieDataSet = new PieDataSet(values, null);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        //отображает знак %
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12.0f);

        pieChart.setData(data);
        pieChart.invalidate();
    }
}
