package com.pavlovnsk.emotionsdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.EmotionsAdapterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.SimpleItemTouchHelperCallback;

import javax.inject.Inject;

public class ListEmotionsItemActivity extends AppCompatActivity {

    @Inject
    EmotionsAdapterSmall adapterSmall;

    FloatingActionButton plusEmotion;
    RecyclerView recyclerViewEmotions;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(this)).build();
        component.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emotions_item);

        recyclerViewEmotions = findViewById(R.id.recycler_view_emotions_item);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        plusEmotion = findViewById(R.id.btn_plus_emotion);
        plusEmotion.setOnClickListener(plusEmotionListener);

        recyclerViewEmotions.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmotions.setAdapter(adapterSmall);
        //recyclerViewEmotions.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapterSmall);

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewEmotions);
    }

    private FloatingActionButton.OnClickListener plusEmotionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(ListEmotionsItemActivity.this, AddEmotion.class));
        }
    };
}
