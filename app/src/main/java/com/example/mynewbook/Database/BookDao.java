package com.example.mynewbook.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book_table")
    List<Book> loadAllBook();

    @Query("SELECT * FROM book_table WHERE id=:id")
    Book loadBookWithId(int id);

    @Query("SELECT * FROM book_table WHERE book_favorited=1")
    List<Book> loadFavoritedBooks();

    @Query("SELECT * FROM book_table WHERE book_rated")
    List<Book> loadRatedBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Update
    void update(Book... book);

    @Delete
    void deleteBook(Book book);


}
