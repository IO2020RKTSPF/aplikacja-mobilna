package com.io2020.PodzielSieKsiazka.adapters;


import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.EnumLocalizer;
import com.io2020.PodzielSieKsiazka.MainActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitAPI;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;

import com.io2020.PodzielSieKsiazka.schemas.AppUser;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionListRecyclerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<TransactionListRecyclerAdapter.ImageViewHolder> {

    private List<Transaction> transactionList;
    private Context context;

    public List<Transaction> getTransactionList(){
        return transactionList;
    }

    public TransactionListRecyclerAdapter(Context context){
        this.context = context;
    }

    public void fillBookList(){

        Call<List<Transaction>> call = RetrofitInstance.GetInstance().GetAPI().getAllTransactions("Bearer " + MainActivity.token);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                transactionList = response.body();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        try {
            holder.bookTitle.setText(transactionList.get(position).getBook().getTitle());
            if(transactionList.get(position).getCustomer().getId() == MainActivity.userID){
                holder.bookCustomer.setVisibility(View.GONE);
            }
            holder.bookCustomer.setText(transactionList.get(position).getCustomer().getName());
            holder.transactionStatus.setText(EnumLocalizer.LocalizeTransactionStatus(transactionList.get(position).getStatus(), context));
        } catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        try {
            return transactionList.size();
        } catch (Exception e){
            Log.d("transactionList: ", e.getMessage());
            return 0;
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookCustomer;
        TextView transactionStatus;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.bookcover);
            bookTitle = itemView.findViewById(R.id.booktitle);
            bookCustomer = itemView.findViewById(R.id.bookcustomer);
            transactionStatus = itemView.findViewById(R.id.transactionstatus);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 121, 0, "Delete");
        }
    }

}
