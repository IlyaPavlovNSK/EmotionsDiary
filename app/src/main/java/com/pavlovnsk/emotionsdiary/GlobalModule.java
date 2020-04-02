package com.pavlovnsk.emotionsdiary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pavlovnsk.emotionsdiary.Room.AppRoomDataBase;
import com.pavlovnsk.emotionsdiary.Data.Utils;
import com.pavlovnsk.emotionsdiary.POJO.MenuItem;

import java.util.ArrayList;

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
        itemsMenu.add(new MenuItem("Список эмоций", R.drawable.ic_list));
        itemsMenu.add(new MenuItem("Язык", R.drawable.ic_language));
        itemsMenu.add(new MenuItem("Поделиться", R.drawable.ic_share));
        itemsMenu.add(new MenuItem("О программе", R.drawable.ic_about));

        return itemsMenu;
    }

    @Provides
    @Singleton
    AppRoomDataBase getAppDatabase() {
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            public void onCreate(SupportSQLiteDatabase db) {
                Utils.createDB(db, context);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        return Room.databaseBuilder(context, AppRoomDataBase.class, "AppRoomDataBase").addCallback(callback).build();
    }

}

