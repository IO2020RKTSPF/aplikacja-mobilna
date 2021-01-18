package com.io2020.PodzielSieKsiazka.ui.yourList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.MainActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.adapters.BookListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.adapters.YourListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.ui.home.HomeViewModel;

public class YourListFragment extends Fragment {

    private YourListViewModel yourListViewModel;
    private RecyclerView yourListRecyclerView;
    private YourListRecyclerAdapter yourListRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourListViewModel =
                new ViewModelProvider(this).get(YourListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourlist, container, false);

        yourListRecyclerView = root.findViewById(R.id.yourListRecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 1);
        yourListRecyclerView.setHasFixedSize(true);
        yourListRecyclerView.setLayoutManager(layoutManager);

        yourListRecyclerAdapter = new YourListRecyclerAdapter();

        yourListRecyclerView.setAdapter(yourListRecyclerAdapter);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        yourListRecyclerAdapter.fillBookList();
    }
}