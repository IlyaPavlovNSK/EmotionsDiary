package com.pavlovnsk.emotionsdiary.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.Fragments.EmotionDialog;
import com.pavlovnsk.emotionsdiary.R;

public class EmotionViewHolder extends RecyclerView.ViewHolder implements EmotionRowView, SeekBar.OnSeekBarChangeListener {

    private TextView emotionLevel;
    private TextView emotionName;
    private ImageFilterView emotionPic;
    private SeekBar emotionSeekBar;
    private Context context;

    public EmotionViewHolder(@NonNull View itemView) {
        super(itemView);
        emotionLevel = itemView.findViewById(R.id.emotion_level);
        emotionName = itemView.findViewById(R.id.emotion_name);
        emotionPic = itemView.findViewById(R.id.emotion_pic);
        emotionSeekBar = itemView.findViewById(R.id.emotion_seekbar);

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
    public void setPictureId(int id) {
        emotionPic.setImageResource(id);
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

        EmotionDialog emotionDialog = new EmotionDialog();
        emotionDialog.setArguments(args);

        emotionDialog.onCreateEmotionDialog(args, context).show();
    }
}
