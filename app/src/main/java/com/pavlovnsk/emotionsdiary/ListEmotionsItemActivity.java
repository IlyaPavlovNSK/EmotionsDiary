package com.pavlovnsk.emotionsdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.EmotionsAdapterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.EmotionsListPresenterSmall;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall.SimpleItemTouchHelperCallback;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListEmotionsItemActivity extends AppCompatActivity {

    private EmotionsAdapterSmall adapterSmall;
    private Disposable disposable;
    private RecyclerView recyclerViewEmotions;

    @Inject
    AppRoomDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(this)).build();
        component.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emotions_item);

        recyclerViewEmotions = findViewById(R.id.recycler_view_emotions_item);

        disposable = db.emotionForItemDao().getEmotionsItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(list -> {
                    adapterSmall = new EmotionsAdapterSmall(new EmotionsListPresenterSmall(list), list, db);
                    recyclerViewEmotions.setAdapter(adapterSmall);
                    recyclerViewEmotions.setLayoutManager(new LinearLayoutManager(this));
                    adapterSmall.notifyDataSetChanged();

                    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapterSmall);
                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerViewEmotions);
                });

        FloatingActionButton plusEmotion = findViewById(R.id.btn_plus_emotion);
        plusEmotion.setOnClickListener(plusEmotionListener);
    }

    private FloatingActionButton.OnClickListener
            plusEmotionListener = view -> startActivity(new Intent(ListEmotionsItemActivity.this, AddEmotion.class));

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ListEmotionsItemActivity.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
        db.close();
    }
}
