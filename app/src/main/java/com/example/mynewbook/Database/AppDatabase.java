package com.example.mynewbook.Database;

import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = Book.class, version = 1, exportSchema = false)


public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized ((AppDatabase.class)) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "MyNewBook")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract BookDao getBookDao();

}
