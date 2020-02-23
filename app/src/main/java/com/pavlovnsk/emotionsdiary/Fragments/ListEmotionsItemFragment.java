package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Adapters.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsAdapterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.Adapters.SimpleItemTouchHelperCallback;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class ListEmotionsItemFragment extends Fragment {

    @Inject
    EmotionsAdapterSmall adapterSmall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.fragment_list_emotions_item, container, false);
        RecyclerView recyclerViewEmotions = view.findViewById(R.id.recycler_view_emotions_item);
        FloatingActionButton plusEmotion = view.findViewById(R.id.btn_plus_emotion);
        plusEmotion.setOnClickListener(plusEmotionListener);

        recyclerViewEmotions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEmotions.setAdapter(adapterSmall);
        recyclerViewEmotions.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapterSmall);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewEmotions);

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
