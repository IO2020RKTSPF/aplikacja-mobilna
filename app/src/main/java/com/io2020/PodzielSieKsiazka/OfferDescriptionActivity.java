package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OfferDescriptionActivity extends AppCompatActivity {

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

        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        owner.setText(intent.getStringExtra("owner"));
        description.setText(intent.getStringExtra("description"));

    }
}