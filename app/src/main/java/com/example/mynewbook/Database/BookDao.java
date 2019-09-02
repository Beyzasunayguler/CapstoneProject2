package com.example.mynewbook.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book_table ORDER BY id")
    List<Book> loadAllBook();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Delete
    void deleteBook(Book book);


}
