package com.pavlovnsk.emotionsdiary.Adapters.EmotionListMain;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionDialog;
import com.pavlovnsk.emotionsdiary.R;

public class EmotionViewHolder extends RecyclerView.ViewHolder implements EmotionRowView, SeekBar.OnSeekBarChangeListener {

    private TextView emotionLevel;
    private TextView emotionName;
    private TextView emotionDescription;
    private ImageFilterView emotionPic;
    private Context context;

    EmotionViewHolder(@NonNull View itemView) {
        super(itemView);
        emotionLevel = itemView.findViewById(R.id.emotion_level);
        emotionName = itemView.findViewById(R.id.emotion_name);
        emotionPic = itemView.findViewById(R.id.emotion_pic);
        SeekBar emotionSeekBar = itemView.findViewById(R.id.emotion_seekbar);
        emotionDescription = itemView.findViewById(R.id.emotion_description);

        context = itemView.getContext();

        emotionSeekBar.setOnSeekBarChangeListener(this);
        emotionSeekBar.getThumb().mutate().setAlpha(0);
    }

    @Override
    public void setEmotionLevel(String level) {
        emotionLevel.setText(level);
    }

    @Override
    public void setEmotionName(String name) {
        emotionName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        emotionDescription.setText(description);
    }

    //ЗАГРУЖЕМ ЧЕРЕЗ BITMAP
    @Override
    public void setPictureBitmap(String bitmapString) {
        Bitmap bitmap = Utils.decodeSampledBitmapFromBd(bitmapString);
        emotionPic.setImageBitmap(bitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        emotionLevel.setText((i-100) + " %");
        emotionPic.setSaturation(i / 100.0f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Bundle args = new Bundle();
        args.putString("name", emotionName.getText().toString());
        args.putString("level", emotionLevel.getText().toString());
        args.putString("description", emotionDescription.getText().toString());

        EmotionDialog emotionDialog = new EmotionDialog();
        emotionDialog.setArguments(args);

        emotionDialog.onCreateEmotionDialog(args, context).show();
    }
}
