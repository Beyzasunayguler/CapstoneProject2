package com.example.mynewbook.Fragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.example.mynewbook.MainActivity;
import com.example.mynewbook.R;

import java.util.List;

public class FragmentMostRated extends Fragment {
    AppDatabase database;
    private ProgressBar loadingBar;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mostRatedView = inflater.inflate(R.layout.fragment_added, container, false);
        mRecyclerView = (RecyclerView) mostRatedView.findViewById(R.id.myRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        loadingBar = (ProgressBar) mostRatedView.findViewById(R.id.loadingBar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((MainActivity) getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        database = Room.databaseBuilder(getContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        final List<Book> books = database.getBookDao().loadAllBook();

        return mostRatedView;
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
