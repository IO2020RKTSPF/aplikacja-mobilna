package com.io2020.PodzielSieKsiazka;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.TransactionChange;
import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionUpdater {
    public static void UpdateTransactionStatus(int transactionId, TransactionStatus status){
        if(transactionId != -1) {
            TransactionChange change = new TransactionChange();
            change.Status = status;
            Call<Transaction> call = RetrofitInstance.GetInstance().GetAPI().changeTransaction("Bearer " + MainActivity.token, transactionId, change);
            call.enqueue(new Callback<Transaction>() {
                @Override
                public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                }

                @Override
                public void onFailure(Call<Transaction> call, Throwable t) {
                }
            });
        }
    }
}
