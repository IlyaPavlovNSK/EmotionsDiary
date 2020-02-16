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

import javax.inject.Inject;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SimpleDateFormat formatForDateNow = Utils.DATEFORMAT;

    @Inject
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

        String time =  formatForDateNow.format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME, item.getEmotionName().trim());
        contentValues.put(Utils.KEY_LEVEL, item.getEmotionLevel().trim());
        contentValues.put(Utils.KEY_DATE, time.trim());

        db.insert(Utils.TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<EmotionItem> getEmotions (Date date1, Date date2){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<EmotionItem> emotions = new ArrayList<>();

        String selectEmotions = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectEmotions, null);

        if (cursor.moveToFirst()){
            do {
                try {
                    if((formatForDateNow.parse(cursor.getString(3).trim()).before(date2)
                            && formatForDateNow.parse(cursor.getString(3).trim()).after(date1))
                                 || (cursor.getString(3).trim().compareTo(formatForDateNow.format(date1))==0)
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
        String count = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        db.close();
        cursor.close();
        return cursor.getCount();
    }
}


