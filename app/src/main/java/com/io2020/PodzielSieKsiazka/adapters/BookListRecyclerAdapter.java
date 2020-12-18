package com.io2020.PodzielSieKsiazka.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitAPI;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookListRecyclerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<BookListRecyclerAdapter.ImageViewHolder> {

    private List<Book> bookList;

    public BookListRecyclerAdapter(){
        fillBookList();
    }

    private void fillBookList(){
        Call<List<Book>> call = RetrofitInstance.GetAPI().getAllBooksList();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                bookList = response.body();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        try {
            holder.bookTitle.setText(bookList.get(position).getTitle());
            holder.bookAuthor.setText(bookList.get(position).getAuthor());
            holder.bookLocation.setText("Bielsko-Biała");
            holder.bookOwner.setText(bookList.get(position).getOwner().getName());
        } catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        try {
            return bookList.size();
        } catch (Exception e){
            return 0;
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookLocation;
        TextView bookAuthor;
        TextView bookOwner;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.bookcover);
            bookTitle = itemView.findViewById(R.id.booktitle);
            bookLocation = itemView.findViewById(R.id.booklocation);
            bookAuthor = itemView.findViewById(R.id.bookauthor);
            bookOwner = itemView.findViewById(R.id.bookowner);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete");
        }
    }

}
