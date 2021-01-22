package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.TransactionSend;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDescriptionActivity extends AppCompatActivity {

    private int currentOfferId;
    TextView numberOfDays;

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
        TextView category = findViewById(R.id.bookCategory);
        numberOfDays = findViewById(R.id.editTextNumber);

        currentOfferId = intent.getIntExtra("id", -1);

        Button button = findViewById(R.id.buttonSendOffer);
        if(MainActivity.userID == intent.getIntExtra("ownerId", -1)){
            button.setVisibility(View.GONE);
        }

        description.setMovementMethod(new ScrollingMovementMethod());

        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        owner.setText(intent.getStringExtra("owner"));
        description.setText(intent.getStringExtra("description"));
        category.setText(intent.getStringExtra("category"));

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
        if(numberOfDays.getText().toString().equals("")){
            numberOfDays.setText("");
            numberOfDays.requestFocus();
            return;
        }
        int days = Integer.parseInt(numberOfDays.getText().toString());
        if(days < 100) {
            if (currentOfferId != -1) {
                TransactionSend transactionSend = new TransactionSend();
                transactionSend.setBookId(currentOfferId);
                transactionSend.setDaysOfRentalTime(days);
                Call<Transaction> call = RetrofitInstance.GetAPI().sendTransactionOffer("Bearer " + MainActivity.token, transactionSend);
                call.enqueue(new Callback<Transaction>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        if (response.isSuccessful()) {
                            Button button = findViewById(R.id.buttonSendOffer);
                            button.setText(R.string.requestSent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable t) {

                    }
                });
            }
        } else {
            numberOfDays.setText("");
            numberOfDays.requestFocus();
        }
    }
}