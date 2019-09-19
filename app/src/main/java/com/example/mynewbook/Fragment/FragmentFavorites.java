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


import com.example.mynewbook.Database.AppDatabase;
import com.example.mynewbook.Database.Book;
import com.example.mynewbook.FavoriteAdapter;
import com.example.mynewbook.R;

import java.util.List;

public class FragmentFavorites extends Fragment {
    AppDatabase database;
    FavoriteAdapter favoriteAdapter;
    private ProgressBar loadingBar;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.fragment_added, container, false);
        mRecyclerView = (RecyclerView) favoritesView.findViewById(R.id.myRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        loadingBar = (ProgressBar) favoritesView.findViewById(R.id.loadingBar);
        favoriteAdapter = new FavoriteAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(favoriteAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        database = Room.databaseBuilder(getContext(), AppDatabase.class, "mydb")
                .allowMainThreadQueries()
                .build();
        final List<Book> books = database.getBookDao().loadAllBook();
        favoriteAdapter.setBookData(books);
        loadingBar.setVisibility(View.GONE);

        return favoritesView;

    }

    @Override
    public void onResume() {
        super.onResume();
        final List<Book> books = database.getBookDao().loadAllBook();

        if (!books.isEmpty()) {
            mRecyclerView.setAdapter(favoriteAdapter);
        }
    }
}
