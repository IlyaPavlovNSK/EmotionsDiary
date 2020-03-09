package com.pavlovnsk.emotionsdiary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pavlovnsk.emotionsdiary.Data.AppDataBase;
import com.pavlovnsk.emotionsdiary.Data.DataBaseHelper;
import com.pavlovnsk.emotionsdiary.Data.EmotionForItemDao;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.POJO.EmotionForItem;
import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;
import com.pavlovnsk.emotionsdiary.POJO.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Executors;

import javax.inject.Named;
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
    ArrayList<EmotionItem> getArray() {
        return new ArrayList<EmotionItem>();
    }

    @Provides
    @Singleton
    @Named("item")
    ArrayList<EmotionItem> createEmotions() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        return dataBaseHelper.getEmotionsItem();
    }

    @Provides
    @Singleton
    @Named("all")
    ArrayList<EmotionItem> getAllEmotions() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        ArrayList<EmotionItem> arrayList = dataBaseHelper.getAllEmotions();
        Collections.reverse(arrayList);
        return arrayList;
    }

    @Provides
    @Singleton
    ArrayList<MenuItem> getMenuItems() {
        ArrayList<MenuItem> itemsMenu = new ArrayList<>();
        itemsMenu.add(new MenuItem("Список эмоций", R.drawable.ic_list));
        itemsMenu.add(new MenuItem("Язык", R.drawable.ic_language));
        itemsMenu.add(new MenuItem("Поделиться", R.drawable.ic_share));
        itemsMenu.add(new MenuItem("О программе", R.drawable.ic_about));

        return itemsMenu;
    }

    @Provides
    @Singleton
    AppDataBase getAppDatabase(final Context context){
        final ArrayList<EmotionForItem> items = Utils.getEmotionForItem(context);

        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        getAppDatabase(context).emotionForItemDao().addDefaultEmotionItem(items);
                    }
                });
            }
        };
        AppDataBase db =  Room.databaseBuilder(context, AppDataBase.class, "AppDataBase").addCallback(callback).build();
        return db;
    }
}

