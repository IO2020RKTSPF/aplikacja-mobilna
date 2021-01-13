package com.io2020.PodzielSieKsiazka.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
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
import com.io2020.PodzielSieKsiazka.OfferDescriptionActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.RecyclerItemClickListener;
import com.io2020.PodzielSieKsiazka.adapters.BookListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView bookListRecyclerView;
    private BookListRecyclerAdapter bookListRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        bookListRecyclerView = root.findViewById(R.id.bookListRecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 1);
        bookListRecyclerView.setHasFixedSize(true);
        bookListRecyclerView.setLayoutManager(layoutManager);
        bookListRecyclerAdapter = new BookListRecyclerAdapter();
        bookListRecyclerView.setAdapter(bookListRecyclerAdapter);
        bookListRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), bookListRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), OfferDescriptionActivity.class);
                        intent.putExtra("id", bookListRecyclerAdapter.bookList.get(position).getId());
                        intent.putExtra("title", bookListRecyclerAdapter.bookList.get(position).getTitle());
                        intent.putExtra("author", bookListRecyclerAdapter.bookList.get(position).getAuthor());
                        intent.putExtra("owner", bookListRecyclerAdapter.bookList.get(position).getOwner().getName());
                        intent.putExtra("ownerId", bookListRecyclerAdapter.bookList.get(position).getOwner().getId());
                        intent.putExtra("description", bookListRecyclerAdapter.bookList.get(position).getDescription());
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
        bookListRecyclerAdapter.fillBookList();
    }
}