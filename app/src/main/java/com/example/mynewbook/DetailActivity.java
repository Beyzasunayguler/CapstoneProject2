package com.example.mynewbook;

import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ShareCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.squareup.picasso.Picasso;

import java.io.File;

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
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
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
                //int=0 ekle numberofrating save e her tıklandığında sayıda çağır birtane ekle çağır
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
                    Toast.makeText(DetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                    Toast.makeText(DetailActivity.this, "Added!", Toast.LENGTH_SHORT).show();

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
