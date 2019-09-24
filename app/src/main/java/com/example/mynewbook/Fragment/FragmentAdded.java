package com.example.mynewbook.Fragment;


import androidx.room.Room;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mynewbook.BookAdapter;
import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.example.mynewbook.R;

import java.util.List;


public class FragmentAdded extends Fragment {
    AppDatabase database;
    BookAdapter bookAdapter;
    private ProgressBar loadingBar;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View addedView = inflater.inflate(R.layout.fragment_added, container, false);
        mRecyclerView = (RecyclerView) addedView.findViewById(R.id.myRecyclerView);
        loadingBar = (ProgressBar) addedView.findViewById(R.id.loadingBar);
        bookAdapter = new BookAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(bookAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        database = Room.databaseBuilder(getContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        final List<Book> books = database.getBookDao().loadAllBook();
        bookAdapter.setBookData(books);
        loadingBar.setVisibility(View.GONE);
        return addedView;
    }

    @Override
    public void onResume() {
        super.onResume();
        final List<Book> books = database.getBookDao().loadAllBook();

        if (!books.isEmpty()) {
            bookAdapter.setBookData(books);
        }
    }


}
