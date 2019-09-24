package com.example.mynewbook;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynewbook.Database.Book;

import java.util.ArrayList;
import java.util.List;

public class MostRatedAdapter extends RecyclerView.Adapter<MostRatedAdapter.BookHolder> {
    private List<Book> bookData = new ArrayList<>();

    public void setBookData(List<Book> bookData) {
        this.bookData.clear();
        this.bookData.addAll(bookData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MostRatedAdapter.BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.most_rated_list_item, viewGroup, false);
        return new MostRatedAdapter.BookHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MostRatedAdapter.BookHolder bookHolder, int i) {
        Book book = bookData.get(i);
        bookHolder.bind(book);

    }

    @Override
    public int getItemCount() {
        return bookData.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        public BookHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final Book book) {
            itemView.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(itemView.getContext(), DetailActivity.class);
                    shareIntent.putExtra(IntentConstants.BOOK_NAME_KEY, book.bookName);
                    shareIntent.putExtra(IntentConstants.BOOK_COMMENT_KEY, book.bookComment);
                    shareIntent.putExtra(IntentConstants.BOOK_NOVELIST_KEY, book.bookNovelist);
                    shareIntent.putExtra(IntentConstants.BOOK_ID_KEY, book.id);
                    shareIntent.putExtra(IntentConstants.BOOK_IMAGE, book.bookImage);
                    itemView.getContext().startActivity(shareIntent);

                }
            });
            TextView textView = itemView.findViewById(R.id.textView);
            textView.setText(book.bookName);
        }
    }
}
