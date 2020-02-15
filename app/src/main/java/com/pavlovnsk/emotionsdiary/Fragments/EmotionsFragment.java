package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsAdapter;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsListPresenter;
import com.pavlovnsk.emotionsdiary.ArrayEmotions;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.ArrayList;

public class EmotionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emotion, container, false);

        RecyclerView recyclerViewEmotions = view.findViewById(R.id.recycler_view_emotions);

        ArrayList <EmotionItem> emotionItems = ArrayEmotions.createEmotions();

        EmotionsAdapter emotionsAdapter = new EmotionsAdapter(new EmotionsListPresenter(emotionItems));
        SkidRightLayoutManager layoutManager = new SkidRightLayoutManager(1.5f, 0.8f);

        recyclerViewEmotions.setLayoutManager(layoutManager);
        recyclerViewEmotions.setAdapter(emotionsAdapter);
        recyclerViewEmotions.setHasFixedSize(true);

        return view;
    }
}
