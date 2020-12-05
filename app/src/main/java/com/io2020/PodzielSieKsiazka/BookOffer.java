package com.io2020.PodzielSieKsiazka;

import android.content.Context;
import android.content.Intent;

import com.io2020.PodzielSieKsiazka.schemas.Book;

public class BookOffer {
    private Book book;

    public static void Create(Context mContext){
        Intent intent = new Intent(mContext, BookOfferCreationActivity.class);
        mContext.startActivity(intent);

    }

    public static void Send(){

    }
}
