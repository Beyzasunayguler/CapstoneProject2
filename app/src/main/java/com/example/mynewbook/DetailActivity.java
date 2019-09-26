package com.example.mynewbook;

import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    AppDatabase database;
    private TextView bookNovelistText;
    private TextView bookCommentText;
    private TextView bookNameText;
    private Toolbar toolbar;
    private ImageView selectImageView;
    private DatabaseReference mDatabase;
    private List<Long> votes = new ArrayList<>();
    private RatingBar mRatingBar;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = (Toolbar) findViewById(R.id.tool_bar_detail);
        final int id = getIntent().getExtras().getInt(IntentConstants.BOOK_ID_KEY);
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
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button mSaveVoteButton = (Button) findViewById(R.id.saveVoteButton);
        new AsyncTask().execute("my string parameter");

        mSaveVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = database.getBookDao().loadBookWithId(id);
                float newState = mRatingBar.getRating();
                book.setBookRated(newState);
                database.getBookDao().update(book);
                Toast.makeText(DetailActivity.this, "Thank you for voting " + newState, Toast.LENGTH_LONG).show();
                votes.add((long) newState);
                mDatabase.child("books").child(String.valueOf(id)).push().setValue(votes);
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
                i++;
                switch (i) {
                    case 1:
                        Toast.makeText(DetailActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(DetailActivity.this, "Deleted!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(DetailActivity.this, "Added!!", Toast.LENGTH_SHORT).show();
                        break;
                }


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

    private void calculateAverage(List<Long> votes) {
        if (votes.isEmpty()) return;
        Long average = 0l;
        for (Long value : votes) {
            average += value;
        }
        average = average / votes.size();
        mRatingBar.setRating(average);
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

    private class AsyncTask extends android.os.AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            final int id = getIntent().getExtras().getInt(IntentConstants.BOOK_ID_KEY);
            DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                    .child("books").child(String.valueOf(id));
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    // ...
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                    int i = 0;

                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();

                        Long match = (Long) next.child(String.valueOf(i)).getValue();
                        votes.add(match);
                        i++;
                    }
                    calculateAverage(votes);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Toast.makeText(DetailActivity.this,"Something went wrong, check the logs",Toast.LENGTH_SHORT).show();
                }
            };
            mPostReference.addListenerForSingleValueEvent(postListener);
            return null;
        }
    }

}
