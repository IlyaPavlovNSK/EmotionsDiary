package com.pavlovnsk.emotionsdiary.Data;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.room.TypeConverter;

import javax.inject.Inject;

public class BitmapConverter {

    @Inject
    Context context;

    @TypeConverter
    public String BitmapInString(Bitmap bitmap){
        String path = Utils.saveToInternalStorage(bitmap, context);
        return path;
    }

    @TypeConverter
    public Bitmap StringInBitmap(String path){
        Bitmap bitmap = Utils.decodeSampledBitmapFromBd(path);
        return bitmap;
    }
}
