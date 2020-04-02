package com.pavlovnsk.emotionsdiary.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain.EmotionsAdapter;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain.EmotionsListPresenter;
import com.pavlovnsk.emotionsdiary.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmotionsFragment extends Fragment {

    @Inject
    AppRoomDataBase db;

    private EmotionsAdapter emotionsAdapter;
    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(requireContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.fragment_emotion, container, false);
        RecyclerView recyclerViewEmotions = view.findViewById(R.id.recycler_view_emotions);

        SkidRightLayoutManager layoutManager = new SkidRightLayoutManager(getItemHeightWidthRatio(), 0.8f);
        recyclerViewEmotions.setLayoutManager(layoutManager);
        recyclerViewEmotions.setHasFixedSize(true);

        disposable = db.emotionForItemDao().getEmotionsItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(list -> {
                    emotionsAdapter = new EmotionsAdapter(new EmotionsListPresenter(list));
                    recyclerViewEmotions.setAdapter(emotionsAdapter);

                    emotionsAdapter.notifyDataSetChanged();
                    db.close();
                });
        return view;
    }

    private float getItemHeightWidthRatio() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return (float) height / width;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }
}
