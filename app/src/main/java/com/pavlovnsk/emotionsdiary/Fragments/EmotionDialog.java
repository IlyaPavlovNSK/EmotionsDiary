package com.pavlovnsk.emotionsdiary.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pavlovnsk.emotionsdiary.R;

public class EmotionDialog extends DialogFragment {

    public Dialog onCreateEmotionDialog(Bundle onSaveInstanceSave, Context context){

        String name = getArguments().getString("name");
        String level = getArguments().getString("level");

        if (context!= null){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            return builder
                    .setTitle("Запись эмоции")
                    .setIcon(R.drawable.ic_emotion)
                    .setMessage("Добавить эмоцию " + "\n" + name + " с уровнем " + level + " ?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

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
