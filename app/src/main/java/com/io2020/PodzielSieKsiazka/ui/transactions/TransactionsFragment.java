package com.io2020.PodzielSieKsiazka.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.MainActivity;
import com.io2020.PodzielSieKsiazka.OfferDescriptionActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.RecyclerItemClickListener;
import com.io2020.PodzielSieKsiazka.TransactionDetailsActivity;
import com.io2020.PodzielSieKsiazka.adapters.TransactionListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.adapters.YourListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.ui.yourList.YourListViewModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsFragment extends Fragment {

    private TransactionsViewModel transactionsViewModel;
    private RecyclerView transactionListRecyclerView;
    private TransactionListRecyclerAdapter transactionListRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionsViewModel =
                new ViewModelProvider(this).get(TransactionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_transactions, container, false);
        transactionListRecyclerView = root.findViewById(R.id.transactionListRecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 1);
        transactionListRecyclerView.setHasFixedSize(true);
        transactionListRecyclerView.setLayoutManager(layoutManager);

        transactionListRecyclerAdapter = new TransactionListRecyclerAdapter(getContext());

        transactionListRecyclerView.setAdapter(transactionListRecyclerAdapter);
        transactionListRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), transactionListRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        List<Transaction> transactions = transactionListRecyclerAdapter.getTransactionList();
                        Intent intent = new Intent(getContext(), TransactionDetailsActivity.class);
                        intent.putExtra("customer", transactions.get(position).getCustomer().getName());
                        intent.putExtra("title", transactions.get(position).getBook().getTitle());
                        intent.putExtra("transactionId", transactions.get(position).getId());
                        int customerId = transactions.get(position).getCustomer().getId();
                        intent.putExtra("isOwner", customerId != MainActivity.userID);
                        String endDate = transactions.get(position).getDateTimeEnd();
                        intent.putExtra("endDate", endDate.split("T")[0]);
                        intent.putExtra("owner", transactions.get(position).getBook().getOwner().getName());
                        intent.putExtra("status", transactions.get(position).getStatus().toString());
                        intent.putExtra("roomId", transactions.get(position).getRoomId());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        transactionListRecyclerAdapter.fillBookList();
    }
}