package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.BookCategory;

public class FilteringActivity extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.offerCategoryLayout).findViewById(R.id.offerSpinner);
        spinner.setAdapter(new ArrayAdapter<BookCategory>(this, android.R.layout.simple_spinner_dropdown_item, BookCategory.values()));


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

    public void applyFilter(View view){
        Intent returnIntent = new Intent();

        String category = spinner.getSelectedItem().toString();
        returnIntent.putExtra("category", category);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}