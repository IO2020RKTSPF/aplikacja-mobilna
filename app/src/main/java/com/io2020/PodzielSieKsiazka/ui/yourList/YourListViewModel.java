package com.io2020.PodzielSieKsiazka.ui.yourList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}