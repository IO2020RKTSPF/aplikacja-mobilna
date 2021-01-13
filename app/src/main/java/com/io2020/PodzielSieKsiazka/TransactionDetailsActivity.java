package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.TransactionChange;
import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailsActivity extends AppCompatActivity {

    private int transactionId;
    private boolean isOwner;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Intent intent = getIntent();

        status = intent.getStringExtra("status");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView bookTitle = findViewById(R.id.transactionBookTitle);
        bookTitle.setText(intent.getStringExtra("title"));

        TextView rentalTime = findViewById(R.id.rentalTime);
        rentalTime.setText(getString(R.string.transactionTime) + " " + intent.getStringExtra("endDate"));

        isOwner = intent.getBooleanExtra("isOwner",false);

        if(isOwner) {
            getSupportActionBar().setTitle(getString(R.string.transactionDetailsTitleOwner) + " " + intent.getStringExtra("customer"));
        } else {
            getSupportActionBar().setTitle(getString(R.string.transactionDetailsTitleCustomer) + " " + intent.getStringExtra("owner"));
        }

        if(!isOwner){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        if(status.equals(TransactionStatus.Declined.toString())){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        if(isOwner && status.equals(TransactionStatus.Accepted.toString())){
            Button cancelButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonDecline);
            cancelButton.setVisibility(View.GONE);
            Button acceptButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonAccept);
            acceptButton.setText(R.string.transactionRent);
        }

        if(status.equals(TransactionStatus.Rented.toString()) && isOwner){
            Button cancelButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonDecline);
            cancelButton.setVisibility(View.GONE);
            Button acceptButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonAccept);
            acceptButton.setText(R.string.transactionEnd);
        }

        if(status.equals(TransactionStatus.Finished.toString())){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        displayNotice(status);

        transactionId = intent.getIntExtra("transactionId", -1);
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

    public void acceptOffer(View view){
        if(status.equals(TransactionStatus.Pending.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Accepted);
            finish();
            return;
        }
        if(status.equals(TransactionStatus.Accepted.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Rented);
            finish();
            return;
        }
        if(status.equals(TransactionStatus.Rented.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Finished);
            finish();
        }

    }

    public void declineOffer(View view){
        TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Declined);
        finish();
    }

    private void displayNotice(String status){
        ConstraintLayout noticeLayout = findViewById(R.id.noticeLayout);
        noticeLayout.setVisibility(View.VISIBLE);
        TextView notice = noticeLayout.findViewById(R.id.notice);
        TextView noticeOk = noticeLayout.findViewById(R.id.noticeOK);

        switch (status){
            case "Pending":{
                notice.setText(R.string.noticePending);
                break;
            }
            case "Declined":{
                notice.setText(R.string.noticeDeclined);
                break;
            }
            case "Accepted":{
                notice.setText(R.string.noticeAccepted);
                break;
            }
            case "Rented":{
                notice.setText(R.string.noticeRented);
                break;
            }
            case "Finished":{
                notice.setText(R.string.noticeFinished);
                break;
            }
        }

        noticeOk.setOnClickListener(l -> {
            noticeLayout.setVisibility(View.GONE);
        });
    }
}