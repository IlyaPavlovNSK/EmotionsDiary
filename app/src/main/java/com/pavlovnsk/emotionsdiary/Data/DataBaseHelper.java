package com.pavlovnsk.emotionsdiary.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SimpleDateFormat fullDate = Utils.FULL_DATE;
    private SimpleDateFormat simpleDate = Utils.SIMPLE_DATE;
    private Context context;

    @Inject
    public DataBaseHelper(Context context) {
        super(context, Utils.DATABASE_NAME_HISTORY, null, Utils.DATABASE_VERSION_HISTORY);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_NAME_HISTORY + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_LEVEL + " TEXT,"
                + Utils.KEY_DESCRIPTION + " TEXT,"
                + Utils.KEY_DATE + " TEXT" + ")";

        String CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_NAME_ITEM + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_LEVEL + " TEXT,"
                + Utils.KEY_DESCRIPTION + " TEXT,"
                + Utils.KEY_PIC + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);

        addDefaultItems(sqLiteDatabase, context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //удаление всех файлов из InternalStorage
        clearMyFiles(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.DATABASE_NAME_HISTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME_ITEM);
        onCreate(sqLiteDatabase);
    }

    //CRUD - create, read, update, delete

    private void addDefaultItems(SQLiteDatabase sqLiteDatabase, Context context) {
        ArrayList<EmotionItem> emotionItems = new ArrayList<>();

        emotionItems.add(new EmotionItem(1, "радость", "0 %", "Я испытываю радость", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(2, "удивление", "0 %", "Я испытываю удивление", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(3, "печаль", "0 %", "Я испытываю печаль", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(4, "гнев", "0 %", "Я испытываю гнев", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(5, "отвращение", "0 %", "Я испытываю отвращение", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(6, "презрение", "0 %", "Я испытываю презрение", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem(7, "страх", "0 %", "Я испытываю страх", BitmapFactory.decodeResource(context.getResources(), R.drawable.img_slide_6)));

        for (int i = 0; i < emotionItems.size(); i++) {
            ContentValues contentValues = new ContentValues();
            EmotionItem item = emotionItems.get(i);

            contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
            contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
            contentValues.put(Utils.KEY_DESCRIPTION, item.getDescription().trim());

            String path = saveToInternalStorage(item.getEmotionPic());
            contentValues.put(Utils.KEY_PIC, path);

            sqLiteDatabase.insertOrThrow(Utils.TABLE_NAME_ITEM, null, contentValues);
        }
    }

    //удаление всех файлов из InternalStorage
    private void clearMyFiles(SQLiteDatabase sqLiteDatabase) {
        String selectAllItems = "SELECT * FROM " + Utils.TABLE_NAME_ITEM;
        Cursor cursor = sqLiteDatabase.rawQuery(selectAllItems, null);
        if (cursor.moveToFirst()) {
            do {
                File file = new File(cursor.getString(4));
                file.delete();
            } while (cursor.moveToNext());
        }
    }

    public void addEmotionItem(EmotionItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
        contentValues.put(Utils.KEY_DESCRIPTION, item.getDescription().trim());

        String path = saveToInternalStorage(item.getEmotionPic());
        contentValues.put(Utils.KEY_PIC, path);

        db.insert(Utils.TABLE_NAME_ITEM, null, contentValues);
        db.close();
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, bitmapImage.toString());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    public void addEmotion(EmotionItem item) {
        String time = fullDate.format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
        contentValues.put(Utils.KEY_DATE, time.trim());

        db.insert(Utils.TABLE_NAME_HISTORY, null, contentValues);
        db.close();
    }

    public ArrayList<EmotionItem> getEmotionsItem() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();
        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_ITEM;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if (cursor.moveToFirst()) {
            do {
                EmotionItem emotionItem = new EmotionItem();
                emotionItem.setEmotionId(cursor.getInt(0));
                emotionItem.setEmotionName(cursor.getString(1));
                emotionItem.setEmotionLevel(cursor.getString(2));
                emotionItem.setDescription(cursor.getString(3));
                //путь к bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(4));
                emotionItem.setEmotionPic(bitmap);

                emotions.add(emotionItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return emotions;
    }

    public ArrayList<EmotionItem> getEmotions(Date date1, Date date2) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();

        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_HISTORY;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    String stringDate = cursor.getString(4).substring(0, cursor.getString(4).length()-6).trim();
                    if ((simpleDate.parse(stringDate).before(date2)
                            && simpleDate.parse(stringDate).after(date1))
                                || (stringDate.compareTo(simpleDate.format(date1)) == 0)
                    ) {
                        EmotionItem emotionItem = new EmotionItem();
                        emotionItem.setEmotionName(cursor.getString(1));
                        emotionItem.setEmotionLevel(cursor.getString(2));
                        emotions.add(emotionItem);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return emotions;
    }

    public ArrayList<EmotionItem> getAllEmotions() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();

        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_HISTORY;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if (cursor.moveToFirst()) {
            do {
                EmotionItem emotionItem = new EmotionItem();
                emotionItem.setEmotionName(cursor.getString(1));
                emotionItem.setDescription(cursor.getString(3));
                emotionItem.setDate(cursor.getString(4));
                emotions.add(emotionItem);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();

        }
        return emotions;
    }

        public void deleteEmotionItem ( int id){
            SQLiteDatabase db = this.getWritableDatabase();
            String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_ITEM + " WHERE " + Utils.KEY_ID + " = ?";
            Cursor cursor = db.rawQuery(selectEmotions, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                do {
                    String path = cursor.getString(4);
                    File file = new File(path);
                    file.delete();
                    Log.d("delete", "File - " + file.getAbsolutePath() + " is delete");
                }
                while (cursor.moveToNext());
            }
            db.delete(Utils.TABLE_NAME_ITEM, Utils.KEY_ID + "=?", new String[]{String.valueOf(id)});

            db.close();
            cursor.close();
        }

        public int getEmotionCount () {
            SQLiteDatabase db = this.getReadableDatabase();
            String count = "SELECT * FROM " + Utils.TABLE_NAME_HISTORY;
            Cursor cursor = db.rawQuery(count, null);
            db.close();
            cursor.close();
            return cursor.getCount();
        }

        public int getEmotionItemCount () {
            SQLiteDatabase db = this.getReadableDatabase();
            String count = "SELECT * FROM " + Utils.TABLE_NAME_ITEM;
            Cursor cursor = db.rawQuery(count, null);
            db.close();
            cursor.close();
            return cursor.getCount();
        }
    }






