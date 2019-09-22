package com.example.mynewbook;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    AppDatabase database;
    private TextView bookNovelistText;
    private TextView bookCommentText;
    private TextView bookNameText;
    private Toolbar toolbar;
    private ImageView selectImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        setContentView(R.layout.activity_detail);
        database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        bookNovelistText = (TextView) findViewById(R.id.novelistText);
        bookCommentText = (TextView) findViewById(R.id.commentText);
        bookNameText = (TextView) findViewById(R.id.bookNameText);
        selectImageView = (ImageView) findViewById(R.id.selectImageView);
        final RatingBar mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button mSaveVoteButton = (Button) findViewById(R.id.saveVoteButton);
        mSaveVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String rating = "Rating is :" + mRatingBar.getRating();
               // Toast.makeText(DetailActivity.this, rating, Toast.LENGTH_LONG).show();
                int id = getIntent().getExtras().getInt(IntentConstants.BOOK_ID_KEY);
                Book book = database.getBookDao().loadBookWithId(id);
                float newState = book.bookRating ;
                book.setBookRated(newState);
                database.getBookDao().update(book);
                Toast.makeText(DetailActivity.this,"Thank you for voting",Toast.LENGTH_LONG).show();
            }
        });


        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getExtras().getInt(IntentConstants.BOOK_ID_KEY);
                Book book = database.getBookDao().loadBookWithId(id);
                boolean newState = !book.isBookFavorited;
                book.setBookFavorited(newState);
                database.getBookDao().update(book);
            }
        });


        if (getIntent().getExtras() == null) {
            Toast.makeText(this, "Something went wrong, check the logs", Toast.LENGTH_LONG).show();
        } else {
            final String bookName = getIntent().getExtras().getString(IntentConstants.BOOK_NAME_KEY);
            final String bookComment = getIntent().getExtras().getString(IntentConstants.BOOK_COMMENT_KEY);
            final String bookNovelist = getIntent().getExtras().getString(IntentConstants.BOOK_NOVELIST_KEY);
            final String bookImage = getIntent().getExtras().getString(IntentConstants.BOOK_IMAGE);
            bookNameText.setText(bookName);
            bookCommentText.setText(bookComment);
            bookNovelistText.setText(bookNovelist);
            if (!TextUtils.isEmpty(bookImage)) {
                Picasso.get().load(new File(bookImage)).into(selectImageView);
            }
        }
    }

    public final Intent createShareIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.share_menu);
        menuItem.setIntent(createShareIntent());
        return true;
    }

}
