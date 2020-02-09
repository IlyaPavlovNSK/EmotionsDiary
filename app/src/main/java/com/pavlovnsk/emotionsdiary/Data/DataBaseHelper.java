package com.pavlovnsk.emotionsdiary.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CARS_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + "("
                + Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_LEVEL + " TEXT,"
                + Utils.KEY_DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Utils.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    //CRUD - create, read, update, delete

    public void addEmotion(EmotionItem item){

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        String time =  formatForDateNow.format(dateNow);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel());
        contentValues.put(Utils.KEY_DATE, time);

        db.insert(Utils.TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<EmotionItem> getEmotions (Date dateAfter, Date dateBefore){
        SQLiteDatabase db = this.getReadableDatabase();
        List<EmotionItem> emotions = new ArrayList<>();

        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");

        if (cursor.moveToFirst()){
            do {
                try {
                    if(formatForDateNow.parse(cursor.getString(3)).after(dateAfter)&&formatForDateNow.parse(cursor.getString(3)).before(dateBefore)){

                        EmotionItem emotionItem = new EmotionItem();
                        emotionItem.setEmotionName(cursor.getString(1));
                        emotionItem.setAemotionLevel(cursor.getString(2));
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
        String count = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        db.close();
        cursor.close();
        return cursor.getCount();
    }
}
