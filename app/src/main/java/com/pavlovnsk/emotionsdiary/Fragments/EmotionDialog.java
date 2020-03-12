package com.pavlovnsk.emotionsdiary.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pavlovnsk.emotionsdiary.DaggerEmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.EmotionsFragmentComponent;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Room.AppDataBase6;
import com.pavlovnsk.emotionsdiary.Room.EmotionForHistory;
import com.pavlovnsk.emotionsdiary.R;

import java.util.Date;

import javax.inject.Inject;

public class EmotionDialog extends DialogFragment {

    @Inject
    AppDataBase6 db;

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
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EmotionForHistory emotionForHistory = new EmotionForHistory(name, level, description, new Date().getTime());
                            db.emotionForHistoryDao().addEmotion(emotionForHistory);
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create();
        } else {
            return null;
        }
    }
}
