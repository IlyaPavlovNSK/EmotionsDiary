package com.pavlovnsk.emotionsdiary.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.util.ArrayList;

public class EmotionsAdapter extends RecyclerView.Adapter<EmotionsAdapter.EmotionViewHolder> {

    private ArrayList<EmotionItem> emotions;
    private static int idRes;

    public EmotionsAdapter(ArrayList<EmotionItem> emotions) {
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public EmotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emotion_item, parent, false);
        return new EmotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionViewHolder holder, int position) {
        EmotionItem emotionItem = emotions.get(position);

        holder.emotionLevel.setText(emotionItem.getEmotionLevel());
        holder.emotionName.setText(emotionItem.getEmotionName());
        holder.emotionPic.setImageResource(emotionItem.getEmotionPic());

        idRes = emotionItem.getEmotionPic();
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    static class EmotionViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener {

        private TextView emotionLevel;
        private TextView emotionName;
        private ImageView emotionPic;
        private SeekBar emotionSeekBar;
        private Bitmap mBitmap;

        public EmotionViewHolder(@NonNull View itemView) {
            super(itemView);
            emotionLevel = itemView.findViewById(R.id.emotion_level);
            emotionName = itemView.findViewById(R.id.emotion_name);
            emotionPic = itemView.findViewById(R.id.emotion_pic);
            emotionSeekBar = itemView.findViewById(R.id.emotion_seekbar);

            emotionSeekBar.setOnSeekBarChangeListener(this);
            emotionSeekBar.getThumb().mutate().setAlpha(0);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            emotionLevel.setText((i-100) + " %");

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            loadSaturationBitmap();
        }

        private void loadSaturationBitmap() {
            // TODO Auto-generated method stub
            mBitmap = BitmapFactory.decodeResource(itemView.getContext().getResources(), idRes);
            if (mBitmap != null) {
                int progressSat = emotionSeekBar.getProgress();
                // Saturation, 0=gray-scale. 1=identity
                float saturation = (float) progressSat / 256;
                emotionPic.setImageBitmap(updateSaturation(mBitmap, saturation));
            }
        }

        private Bitmap updateSaturation(Bitmap src, float settingSat) {
            int width = src.getWidth();
            int height = src.getHeight();
            Bitmap bitmapResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvasResult = new Canvas(bitmapResult);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(settingSat);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(filter);
            canvasResult.drawBitmap(src, 0, 0, paint);
            return bitmapResult;
        }
    }
}
