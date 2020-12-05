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

import com.io2020.PodzielSieKsiazka.R;

public class YourListFragment extends Fragment {

    private YourListViewModel yourListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourListViewModel =
                new ViewModelProvider(this).get(YourListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_yourlist, container, false);

        return root;
    }
}