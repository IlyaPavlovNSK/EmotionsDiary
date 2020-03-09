package com.pavlovnsk.emotionsdiary.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmotionDialog extends DialogFragment {
    private SimpleDateFormat fullDate = Utils.FULL_DATE;

    public Dialog onCreateEmotionDialog(Bundle onSaveInstanceSave, final Context context){

        final String name = getArguments().getString("name");
        final String level = getArguments().getString("level");
        final String description = getArguments().getString("description");

        if (context!= null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            return builder
                    .setTitle("Запись эмоции")
                    .setIcon(R.drawable.ic_emotion)
                    .setMessage("Добавить эмоцию " + "\n" + name + " с уровнем " + level + " ?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataBaseHelper dataBase = new DataBaseHelper(context);
                            String time = fullDate.format(new Date());
                            dataBase.addEmotion(new EmotionItem(name, level, description, null, time ));
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create();
        }else {
            return null;
        }
    }
}
