package com.io2020.PodzielSieKsiazka.adapters;


import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.MainActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitAPI;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;

import com.io2020.PodzielSieKsiazka.schemas.AppUser;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourListRecyclerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<YourListRecyclerAdapter.ImageViewHolder> {

    private List<Book> bookList;

    public YourListRecyclerAdapter(){

    }

    public void fillBookList(){

        Call<User> call = RetrofitInstance.GetAPI().getUserById(MainActivity.userID);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                bookList = response.body().getBookList();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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
        } catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        try {
            return bookList.size();
        } catch (Exception e){
            Log.d("yourList: ", e.getMessage());
            return 0;
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookAuthor;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.bookcover);
            bookTitle = itemView.findViewById(R.id.booktitle);
            bookAuthor = itemView.findViewById(R.id.bookauthor);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete");
        }
    }

}
