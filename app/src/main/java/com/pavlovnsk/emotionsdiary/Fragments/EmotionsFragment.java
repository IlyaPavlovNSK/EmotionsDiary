package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Adapters.EmotionsAdapter;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),  RecyclerView.HORIZONTAL, false);
        recyclerViewEmotions.setLayoutManager(layoutManager);

        ArrayList <EmotionItem> emotionItems = ArrayEmotions.createEmotions();

        EmotionsAdapter emotionsAdapter = new EmotionsAdapter(emotionItems);
        recyclerViewEmotions.setAdapter(emotionsAdapter);
        recyclerViewEmotions.setHasFixedSize(true);

        return view;
    }
}
