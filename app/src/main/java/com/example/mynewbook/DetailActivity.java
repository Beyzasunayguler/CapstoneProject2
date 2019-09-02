package com.example.mynewbook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        database =AppDatabase.getDatabase(this.getApplication());
        List<Book> books=database.getBookDao().loadAllBook();
        FloatingActionButton favoriteButton = (FloatingActionButton) findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                   boolean isFavorıted = false;
                    for(Movie temp: movies) {
                        if (temp.mId == id) {
                            isFavorıted = true;
                            break;
                        }
                    }
                if (isFavorıted) {
                    database.movieDao().deleteMovie(currentMovie);
                    Toast.makeText(DetailActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    database.movieDao().insertMovie(currentMovie);
                    Toast.makeText(DetailActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                }
                 */

            }
        });


    }

    public  final Intent createShareIntent(){
        Intent shareIntent= ShareCompat.IntentBuilder.from(this)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.share_menu);
        menuItem.setIntent(createShareIntent());
        return true;
    }

}
