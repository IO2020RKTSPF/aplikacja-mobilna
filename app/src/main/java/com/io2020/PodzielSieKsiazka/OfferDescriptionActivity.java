package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.TransactionSend;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDescriptionActivity extends AppCompatActivity {

    private int currentOfferId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        TextView title = findViewById(R.id.bookTitle);
        TextView author = findViewById(R.id.bookAuthor);
        TextView owner = findViewById(R.id.bookOwner);
        TextView description = findViewById(R.id.bookDescription);

        currentOfferId = intent.getIntExtra("id", -1);

        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        owner.setText(intent.getStringExtra("owner"));
        description.setText(intent.getStringExtra("description"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendRequest(View view){
        if(currentOfferId != -1) {
            TransactionSend transactionSend = new TransactionSend();
            transactionSend.setBookId(currentOfferId);
            transactionSend.setDaysOfRentalTime(5);
            Call<Transaction> call = RetrofitInstance.GetAPI().sendTransactionOffer("Bearer " + MainActivity.token, transactionSend);
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