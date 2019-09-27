package com.example.mynewbook.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book_table")
    LiveData<List<Book>> loadAllBook();

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
