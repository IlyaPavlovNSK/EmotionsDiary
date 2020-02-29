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

import com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList.EmotionHistoryAdapter;
import com.pavlovnsk.emotionsdiary.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class EmotionsHistoryFragment extends Fragment {

    @Inject
    EmotionHistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.emotion_history_fragment, container, false);

        RecyclerView recyclerViewEmotions = view.findViewById(R.id.emotions_history_fragment_rv);
        recyclerViewEmotions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEmotions.setAdapter(adapter);
        recyclerViewEmotions.setHasFixedSize(true);

        return view;
    }
}
