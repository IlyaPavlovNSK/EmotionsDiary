package com.pavlovnsk.emotionsdiary.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SimpleDateFormat formatForDateNow = Utils.DATEFORMAT;
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.DATABASE_NAME_HISTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME_ITEM);
        onCreate(sqLiteDatabase);
    }

    //CRUD - create, read, update, delete

    private void addDefaultItems(SQLiteDatabase sqLiteDatabase, Context context){
        ArrayList<EmotionItem> emotionItems = new ArrayList<>();

        emotionItems.add(new EmotionItem("радость", "0 %", "Я испытываю радость", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("удивление", "0 %", "Я испытываю удивление", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("печаль", "0 %", "Я испытываю печаль", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("гнев", "0 %", "Я испытываю гнев", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("отвращение", "0 %", "Я испытываю отвращение", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("презрение", "0 %", "Я испытываю презрение", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));
        emotionItems.add(new EmotionItem("страх", "0 %", "Я испытываю страх", BitmapFactory.decodeResource(context.getResources(),R.drawable.img_slide_6)));

        for (int i = 0; i < emotionItems.size() ; i++) {
            ContentValues contentValues = new ContentValues();
            EmotionItem item = emotionItems.get(i);

            contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
            contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
            contentValues.put(Utils.KEY_DESCRIPTION, item.getDescription().trim());

            //путь к bitmap
            Uri tempUri = getImageUri(context, item.getEmotionPic());
            File finalFile = new File(getRealPathFromURI(tempUri));
            contentValues.put(Utils.KEY_PIC, finalFile.getAbsolutePath());

            sqLiteDatabase.insertOrThrow(Utils.TABLE_NAME_ITEM, null, contentValues);
        }
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void addEmotionItem(EmotionItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
        contentValues.put(Utils.KEY_DESCRIPTION, item.getDescription().trim());
        //путь к bitmap
        Uri tempUri = getImageUri(context, item.getEmotionPic());
        File finalFile = new File(getRealPathFromURI(tempUri));
        contentValues.put(Utils.KEY_PIC, finalFile.getAbsolutePath());

        db.insert(Utils.TABLE_NAME_ITEM, null, contentValues);
        db.close();
    }

    public void addEmotion(EmotionItem item){
        String time =  formatForDateNow.format(new Date());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
        contentValues.put(Utils.KEY_DATE, time.trim());

        db.insert(Utils.TABLE_NAME_HISTORY, null, contentValues);
        db.close();
    }

    public ArrayList<EmotionItem> getEmotionsItem(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();
        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_ITEM;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if(cursor.moveToFirst()){
            do {
                EmotionItem emotionItem = new EmotionItem();
                emotionItem.setEmotionName(cursor.getString(0));
                emotionItem.setEmotionLevel(cursor.getString(1));
                emotionItem.setDescription(cursor.getString(2));
                //путь к bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(3));
                emotionItem.setEmotionPic(bitmap);

                emotions.add(emotionItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return emotions;
    }

    public ArrayList<EmotionItem> getEmotions (Date date1, Date date2){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();

        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME_HISTORY;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if (cursor.moveToFirst()){
            do {
                try {
                    if((formatForDateNow.parse(cursor.getString(4).trim()).before(date2)
                            && formatForDateNow.parse(cursor.getString(4).trim()).after(date1))
                                 || (cursor.getString(4).trim().compareTo(formatForDateNow.format(date1))==0)
                    ){
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

    public int getEmotionCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT * FROM " + Utils.TABLE_NAME_HISTORY;
        Cursor cursor = db.rawQuery(count, null);
        db.close();
        cursor.close();
        return cursor.getCount();
    }

    public int getEmotionItemCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT * FROM " + Utils.TABLE_NAME_ITEM;
        Cursor cursor = db.rawQuery(count, null);
        db.close();
        cursor.close();
        return cursor.getCount();
    }
}


