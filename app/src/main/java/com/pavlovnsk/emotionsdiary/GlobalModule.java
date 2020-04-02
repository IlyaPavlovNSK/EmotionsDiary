package com.pavlovnsk.emotionsdiary;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.POJO.MenuItem;
import com.pavlovnsk.emotionsdiary.Room.EmotionForItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GlobalModule {

    private Context context;

    public GlobalModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context getSomeContext() {
        return this.context;
    }

    @Provides
    @Singleton
    ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> itemsMenu = new ArrayList<>();
        itemsMenu.add(new MenuItem(context.getString(R.string.list_of_emotions), R.drawable.ic_list));
        itemsMenu.add(new MenuItem(context.getString(R.string.language), R.drawable.ic_language));
        itemsMenu.add(new MenuItem(context.getString(R.string.share), R.drawable.ic_share));
        itemsMenu.add(new MenuItem(context.getString(R.string.about_the_program), R.drawable.ic_about));

        return itemsMenu;
    }

    @Provides
    @Singleton
    AppRoomDataBase getAppDatabase() {
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                createDB(db, context);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        return Room.databaseBuilder(context, AppRoomDataBase.class, "AppRoomDataBase").addCallback(callback).build();
    }

    private void createDB(SupportSQLiteDatabase supportSQLiteDatabase, Context context){

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

    private void getEmotionForItem(SupportSQLiteDatabase supportSQLiteDatabase, Context context){
        List<EmotionForItem> items = new ArrayList<>();
        long date = new Date().getTime();

        items.add(new EmotionForItem(context.getString(R.string.happy), "0 %", context.getString(R.string.i_feel_joy), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.happy), context)));
        items.add(new EmotionForItem(context.getString(R.string.surprise), "0 %", context.getString(R.string.i_am_surprised), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.surprise), context)));
        items.add(new EmotionForItem(context.getString(R.string.sadness), "0 %", context.getString(R.string.i_feel_sad), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.sadness), context)));
        items.add(new EmotionForItem(context.getString(R.string.anger), "0 %", context.getString(R.string.i_am_angry), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.anger), context)));
        items.add(new EmotionForItem(context.getString(R.string.disgust), "0 %", context.getString(R.string.i_am_disgusted), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.disgust), context)));
        items.add(new EmotionForItem(context.getString(R.string.contempt), "0 %", context.getString(R.string.i_feel_contempt), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.contempt), context)));
        items.add(new EmotionForItem(context.getString(R.string.fear), "0 %", context.getString(R.string.i_am_afraid), date, Utils.saveToInternalStorage(Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.fear), context)));

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
}

