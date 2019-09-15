package com.example.mynewbook.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.example.mynewbook.R;

import java.util.List;

public class FragmentMostRated extends Fragment {
    AppDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_most_rated, viewGroup, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        final List<Book> books = database.getBookDao().loadAllBook();

        if (!books.isEmpty()) {
           // Toast.makeText(this, books.get(books.size() - 1).bookName, Toast.LENGTH_SHORT).show();
        }
    }
}
