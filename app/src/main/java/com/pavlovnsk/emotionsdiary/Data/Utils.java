package com.pavlovnsk.emotionsdiary.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.OnConflictStrategy;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.pavlovnsk.emotionsdiary.R;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    private static final String TABLE_NAME_ITEM = "EmotionForItem";

    private static final String KEY_ID = "emotionId";
    private static final String KEY_NAME = "emotionName";
    private static final String KEY_LEVEL = "emotionLevel";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PIC = "emotionPic";

    public static final SimpleDateFormat FULL_DATE = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DATE_FOR_ROOM = new SimpleDateFormat("yyyy.MM.dd.HH.mm");

    static Bitmap decodeSampledBitmapFromResource(Resources res, int id) {
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

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    private static void getEmotionForItem(SupportSQLiteDatabase supportSQLiteDatabase, Context context){
        List<EmotionForItem> items = new ArrayList<>();
        long date = new Date().getTime();
        items.add(new EmotionForItem("радость", "0 %", "Я испытываю радость", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.happy), context)));
        items.add(new EmotionForItem("удивление", "0 %", "Я испытываю удивление", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.surprise), context)));
        items.add(new EmotionForItem("печаль", "0 %", "Я испытываю печаль", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.sadness), context)));
        items.add(new EmotionForItem("гнев", "0 %", "Я испытываю гнев", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.anger), context)));
        items.add(new EmotionForItem("отвращение", "0 %", "Я испытываю отвращение", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.disgust), context)));
        items.add(new EmotionForItem("презрение", "0 %", "Я испытываю презрение", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.contempt), context)));
        items.add(new EmotionForItem("страх", "0 %", "Я испытываю страх", date, saveToInternalStorage(decodeSampledBitmapFromResource(context.getResources(), R.drawable.fear), context)));

        for (int i = 0; i < items.size(); i++) {
            ContentValues contentValues = new ContentValues();
            EmotionForItem item = items.get(i);

            contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
            contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
            contentValues.put(Utils.KEY_DESCRIPTION, item.getDescription().trim());
            contentValues.put(Utils.KEY_DATE, item.getDate());
            contentValues.put(Utils.KEY_PIC, item.getEmotionPic());

            supportSQLiteDatabase.insert(Utils.TABLE_NAME_ITEM, OnConflictStrategy.IGNORE, contentValues);
        }
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

    public static void createDB(SupportSQLiteDatabase supportSQLiteDatabase, Context context){

        String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_NAME_ITEM + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_LEVEL + " TEXT,"
                + Utils.KEY_DESCRIPTION + " TEXT,"
                + Utils.KEY_DATE + " INTEGER,"
                + Utils.KEY_PIC + " TEXT" + ")";

        supportSQLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        getEmotionForItem(supportSQLiteDatabase, context);
    }
}
