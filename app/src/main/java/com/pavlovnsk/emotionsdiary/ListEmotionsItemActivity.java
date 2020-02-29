package com.pavlovnsk.emotionsdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.EmotionsAdapterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.EmotionsListPresenterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.SimpleItemTouchHelperCallback;
import com.pavlovnsk.emotionsdiary.Fragments.ListEmotionsItemView;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

import javax.inject.Inject;

public class ListEmotionsItemActivity extends AppCompatActivity implements ListEmotionsItemView {

    @Inject
    EmotionsAdapterSmall adapterSmall;
    @Inject
    EmotionsListPresenterSmall emotionsListPresenterSmall;

    FloatingActionButton plusEmotion;
    RecyclerView recyclerViewEmotions;
    CoordinatorLayout coordinatorLayout;
    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Удалено: ", Snackbar.LENGTH_SHORT);

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
        recyclerViewEmotions.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(emotionsListPresenterSmall);

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewEmotions);


    }

    private FloatingActionButton.OnClickListener plusEmotionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(ListEmotionsItemActivity.this, AddEmotion.class));
        }
    };

    public void showSnackBar(final EmotionItem item, final int position){
        snackbar.setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotionsListPresenterSmall.returnItem(item, position);
            }
        });
        snackbar.show();
    }
}
