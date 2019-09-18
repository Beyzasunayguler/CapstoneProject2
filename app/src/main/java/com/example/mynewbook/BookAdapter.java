package com.example.mynewbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynewbook.Database.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private List<Book> bookData = new ArrayList<>();

    public void setBookData(List<Book> bookData) {
        this.bookData.clear();
        this.bookData.addAll(bookData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookAdapter.BookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new BookHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookHolder bookHolder, int i) {
        bookHolder.bind(bookData.get(i));
    }

    @Override
    public int getItemCount() {
        return bookData.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public BookHolder(@NonNull final View itemView) {
            super(itemView);
        }

        public void bind(final Book book) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(itemView.getContext(), DetailActivity.class);
                    shareIntent.putExtra(IntentConstants.BOOK_NAME_KEY, book.bookName);
                    shareIntent.putExtra(IntentConstants.BOOK_COMMENT_KEY, book.bookComment);
                    shareIntent.putExtra(IntentConstants.BOOK_NOVELIST_KEY, book.bookNovelist);
                   // shareIntent.putExtra(IntentConstants.BOOK_IMAGE, book);
                    itemView.getContext().startActivity(shareIntent);

                }
            });
            textView = itemView.findViewById(R.id.textView);
            textView.setText(book.bookName);
        }
    }
}
