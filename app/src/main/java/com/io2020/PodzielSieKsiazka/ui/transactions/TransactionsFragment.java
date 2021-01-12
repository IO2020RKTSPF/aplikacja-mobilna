package com.io2020.PodzielSieKsiazka.ui.transactions;

import android.os.Bundle;
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
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.adapters.TransactionListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.adapters.YourListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.ui.yourList.YourListViewModel;

import java.util.List;

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

        transactionListRecyclerAdapter = new TransactionListRecyclerAdapter();

        transactionListRecyclerView.setAdapter(transactionListRecyclerAdapter);

        return root;
    }
}