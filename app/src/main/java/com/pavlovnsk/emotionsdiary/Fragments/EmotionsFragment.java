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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Adapters.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsAdapter;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class EmotionsFragment extends Fragment {

    @Inject
    EmotionsAdapter emotionsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.fragment_emotion, container, false);
        RecyclerView recyclerViewEmotions = view.findViewById(R.id.recycler_view_emotions);
        FloatingActionButton plusEmotion = view.findViewById(R.id.btn_plus_emotion);
        plusEmotion.setOnClickListener(plusEmotionListener);

        SkidRightLayoutManager layoutManager = new SkidRightLayoutManager(1.5f, 0.8f);

        recyclerViewEmotions.setLayoutManager(layoutManager);
        recyclerViewEmotions.setAdapter(emotionsAdapter);
        recyclerViewEmotions.setHasFixedSize(true);

        return view;
    }

    private FloatingActionButton.OnClickListener plusEmotionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment selectedFragment = new AddEmotionFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
    };
}
