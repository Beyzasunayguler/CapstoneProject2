package com.example.mynewbook;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    AppDatabase database;
    private TextView bookNovelistText;
    private TextView bookCommentText;
    private TextView bookNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_detail);
        database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        bookNovelistText=(TextView) findViewById(R.id.novelistText);
        bookCommentText=(TextView) findViewById(R.id.commentText);
        bookNameText=(TextView) findViewById(R.id.bookNameText);
        RatingBar mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button mSaveVoteButton = (Button) findViewById(R.id.saveVoteButton);
        mSaveVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    mRatingBar.setRating(0);
                    Toast.makeText(DetailActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }

               */
            }
        });


        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*
              final String bookName = getIntent().getExtras().getString(IntentConstants.BOOK_NAME_KEY);
               final String bookComment = getIntent().getExtras().getString(IntentConstants.BOOK_COMMENT_KEY);
                final String bookNovelist = getIntent().getExtras().getString(IntentConstants.BOOK_NOVELIST_KEY);
                final String imagePath = getIntent().getExtras().getString(IntentConstants.BOOK_IMAGE);
               // Book currentBook =  new Book()

                   boolean isFavorıted = false;
                    for(Book temp: books) {
                        if (temp.id == id) {
                            isFavorıted = true;
                            break;
                        }
                    }
                if (isFavorıted) {
                    database.getBookDao().deleteBook(books);
                    Toast.makeText(DetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    database.getBookDao().insertBook(books);
                    Toast.makeText(DetailActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                }
               */

            }
        });


        if (getIntent().getExtras() == null) {
            Toast.makeText(this, "Something went wrong, check the logs", Toast.LENGTH_LONG).show();
        }else{
            final String bookName = getIntent().getExtras().getString(IntentConstants.BOOK_NAME_KEY);
            final String bookComment = getIntent().getExtras().getString(IntentConstants.BOOK_COMMENT_KEY);
            final String bookNovelist = getIntent().getExtras().getString(IntentConstants.BOOK_NOVELIST_KEY);
            final String imagePath = getIntent().getExtras().getString(IntentConstants.BOOK_IMAGE);
            bookNameText.setText(bookName);
            bookCommentText.setText(bookComment);
            bookNovelistText.setText(bookNovelist);



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
