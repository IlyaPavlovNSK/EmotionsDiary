package com.pavlovnsk.emotionsdiary.Data;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pavlovnsk.emotionsdiary.POJO.EmotionForItem;
import com.pavlovnsk.emotionsdiary.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utils {

    static final int DATABASE_VERSION_HISTORY = 44;
    static final String DATABASE_NAME_HISTORY = "History_DB";

    static final String TABLE_NAME_HISTORY = "Your_emotions_history";
    static final String TABLE_NAME_ITEM = "Your_emotions_item";

    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_LEVEL = "level";
    static final String KEY_DATE = "date";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_PIC = "pic";

    public static final SimpleDateFormat FULL_DATE = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DATE_FOR_ROOM = new SimpleDateFormat("yyyy.MM.dd.HH.mm");

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int id) {
        int reqWidth = 480;
        int reqHeight = 720;
        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, id, options);
        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, id, options);
    }

    public static Bitmap decodeSampledBitmapFromBd(String path) {
        int reqWidth = 480;
        int reqHeight = 720;
        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static ArrayList<EmotionForItem> getEmotionForItem(Context context){
        final ArrayList<EmotionForItem> items = new ArrayList<>();
        long date = new Date().getTime();
        items.add(new EmotionForItem("радость", "0 %", "Я испытываю радость", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.happy)));
        items.add(new EmotionForItem("удивление", "0 %", "Я испытываю удивление", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.surprise)));
        items.add(new EmotionForItem("печаль", "0 %", "Я испытываю печаль", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.sadness)));
        items.add(new EmotionForItem("гнев", "0 %", "Я испытываю гнев", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.anger)));
        items.add(new EmotionForItem("отвращение", "0 %", "Я испытываю отвращение", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.disgust)));
        items.add(new EmotionForItem("презрение", "0 %", "Я испытываю презрение", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.contempt)));
        items.add(new EmotionForItem("страх", "0 %", "Я испытываю страх", date, Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.fear)));
        return items;
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, Context context) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, bitmapImage.toString());

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bos = new BufferedOutputStream(fos);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                fos.flush();
                bos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }
}
