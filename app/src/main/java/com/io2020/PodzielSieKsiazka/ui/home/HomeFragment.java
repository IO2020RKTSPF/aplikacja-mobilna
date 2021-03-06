package com.io2020.PodzielSieKsiazka.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.io2020.PodzielSieKsiazka.FilteringActivity;
import com.io2020.PodzielSieKsiazka.MainActivity;
import com.io2020.PodzielSieKsiazka.OfferDescriptionActivity;
import com.io2020.PodzielSieKsiazka.R;
import com.io2020.PodzielSieKsiazka.RecyclerItemClickListener;
import com.io2020.PodzielSieKsiazka.adapters.BookListRecyclerAdapter;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.BookCategory;

import retrofit2.http.PATCH;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView bookListRecyclerView;
    private BookListRecyclerAdapter bookListRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String savedCategoryFilter = null;
    EditText searchText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        searchText = root.findViewById(R.id.searchTextField);
        ImageButton searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(l -> bookListRecyclerAdapter.getFilteredBookList(savedCategoryFilter,
                searchText.getText().toString(), null, null, null));

        ImageButton filterButton = root.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(l -> {
            Intent intent = new Intent(getActivity(), FilteringActivity.class);
            startActivityForResult(intent, 1);
        });

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
                        intent.putExtra("category", bookListRecyclerAdapter.bookList.get(position).getCategory().toString());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            savedCategoryFilter = data.getStringExtra("category");
            bookListRecyclerAdapter.getFilteredBookList(savedCategoryFilter,
                    searchText.getText().toString(), null, null, null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bookListRecyclerAdapter.getFilteredBookList(savedCategoryFilter,
                searchText.getText().toString(), null, null, null);
    }
}