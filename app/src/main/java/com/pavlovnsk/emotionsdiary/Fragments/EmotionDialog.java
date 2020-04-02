package com.pavlovnsk.emotionsdiary.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pavlovnsk.emotionsdiary.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.R;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EmotionDialog extends DialogFragment {

    @Inject
    AppRoomDataBase db;

    private Disposable disposable;

    public Dialog onCreateEmotionDialog(Bundle onSaveInstanceSave, final Context context) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(context)).build();
        component.inject(this);

        final String name = getArguments().getString("name");
        final String level = getArguments().getString("level");
        final String description = getArguments().getString("description");

        if (context != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            return builder
                    .setTitle("Запись эмоции")
                    .setIcon(R.drawable.ic_emotion)
                    .setMessage("Добавить эмоцию " + "\n" + name + " с уровнем " + level + " ?")
                    .setPositiveButton("Да", (dialogInterface, i) -> {
                        EmotionForHistory emotionForHistory = new EmotionForHistory(name, level, description, new Date().getTime());

                        disposable = Completable.fromAction(() -> db.emotionForHistoryDao().addEmotion(emotionForHistory))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                        db.close();
                    })
                    .setNegativeButton("Нет", (dialogInterface, i) -> dialogInterface.cancel())
                    .create();
        } else {
            db.close();
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }
}
