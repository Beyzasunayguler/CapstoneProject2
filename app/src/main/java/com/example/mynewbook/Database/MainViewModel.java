package com.example.mynewbook.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final List<Book> favoriteBooks;
    private static final String TAG=MainViewModel.class.getSimpleName();
    AppDatabase database;

    public MainViewModel(@NonNull Application application){
        super(application);
        database=AppDatabase.getDatabase(this.getApplication());
        Log.d(TAG," ");
        favoriteBooks=database.getBookDao().loadFavoritedBooks();
    }
    public List<Book> getFavoriteBooks(){
        return favoriteBooks;
    }
}
