package com.pavlovnsk.emotionsdiary.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList.EmotionHistoryAdapter;
import com.pavlovnsk.emotionsdiary.Adapters.EmotionHistoryList.EmotionHistoryPresenter;
import com.pavlovnsk.emotionsdiary.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.R;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;

import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmotionsHistoryFragment extends Fragment {

    private EmotionHistoryAdapter adapter;
    private Disposable disposable;

    @Inject
    AppRoomDataBase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(getContext())).build();
        component.inject(this);

        View view = inflater.inflate(R.layout.emotion_history_fragment, container, false);

        RecyclerView recyclerViewEmotions = view.findViewById(R.id.emotions_history_fragment_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewEmotions.getContext(), layoutManager.getOrientation());

        recyclerViewEmotions.setLayoutManager(layoutManager);
        recyclerViewEmotions.addItemDecoration(dividerItemDecoration);

        disposable = db.emotionForHistoryDao().getAllEmotions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(list ->{
                    Collections.reverse(list);
                    adapter = new EmotionHistoryAdapter(new EmotionHistoryPresenter(list));
                    recyclerViewEmotions.setAdapter(adapter);
                    recyclerViewEmotions.setHasFixedSize(true);
                    adapter.notifyDataSetChanged();
                    db.close();
                });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }
}
