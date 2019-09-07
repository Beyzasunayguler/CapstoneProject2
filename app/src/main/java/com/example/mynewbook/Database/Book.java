package com.example.mynewbook.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "book_table")
public class Book implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "movie_title")
    public String bookName;
    @ColumnInfo(name = "release_date")
    public String bookNovelist;
    @ColumnInfo(name = "poster_path")
    public String bookComment;

    protected Book(Parcel in) {
        id = in.readInt();
        bookName = in.readString();
        bookNovelist = in.readString();
        bookComment = in.readString();
    }

    public Book(String bookName, String bookComment, String bookNovelist) {
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookNovelist = bookNovelist;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookNovelist() {
        return bookNovelist;
    }

    public void setBookNovelist(String bookNovelist) {
        this.bookNovelist = bookNovelist;
    }

    public String getBookcomment() {
        return bookComment;
    }

    public void setBookComment(String bookComment) {
        this.bookComment = bookComment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(bookComment);
        parcel.writeString(bookName);
        parcel.writeString(bookNovelist);

    }
}
