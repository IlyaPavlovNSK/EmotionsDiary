package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.DaggerStatisticFragmentComponent;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.DataSettings;
import com.pavlovnsk.emotionsdiary.StatisticFragmentUtils.StatisticFragmentComponent;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StatisticsFragment extends Fragment {

    private PieChart pieChart;
    private TextView textViewDate;

    private Disposable disposableItems;
    private Disposable disposableHistory;

    private CompositeDisposable compositeDisposable;

    @Inject
    AppRoomDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StatisticFragmentComponent statisticFragmentComponent = DaggerStatisticFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        statisticFragmentComponent.inject(this);

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        compositeDisposable = new CompositeDisposable();

        pieChart = view.findViewById(R.id.pie_chart);
        FloatingActionButton btnData = view.findViewById(R.id.btn_data);
        textViewDate = view.findViewById(R.id.text_View_Date);

        btnData.setOnClickListener(listener);

        pieChart.setNoDataText(getString(R.string.select_dates));

        return view;
    }

    private FloatingActionButton.OnClickListener listener = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance();
            MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
            CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder().setValidator(new CalendarConstraints.DateValidator() {
                @Override
                public boolean isValid(long date) {
                    return calendar.getTime().after(new Date(date));
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel parcel, int i) {
                }
            }).setEnd(calendar.getTimeInMillis());
            builder.setCalendarConstraints(calendarConstraints.build());
            MaterialDatePicker picker = builder.build();

            if (getFragmentManager() != null) {
                picker.show(getFragmentManager(), "date_picker");
            }

            picker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
                Long first = selection.first;
                Long second = selection.second;

                if (first != null && second != null) {

                    disposableItems = db.emotionForItemDao().getEmotionsItem()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(emotionsItems -> {
                                DataSettings dataSettings = new DataSettings(pieChart, textViewDate, emotionsItems, first, second);
                                dataSettings.writeDate();

                                disposableHistory = db.emotionForHistoryDao().getEmotions(first, second + TimeUnit.DAYS.toMillis(1))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(dataSettings::onDataSelected);

                                compositeDisposable.add(disposableHistory);
                            });
                    compositeDisposable.add(disposableItems);
                }
            });
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable.size() > 0) {
            compositeDisposable.dispose();
        }
        db.close();
    }
}