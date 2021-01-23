package com.io2020.PodzielSieKsiazka;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.BookCategory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookEditActivity extends AppCompatActivity {
    EditText titleTextView;
    EditText authorTextView;
    EditText isbnTextView;
    EditText descriptionTextView;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editOffer);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String category = intent.getStringExtra("category");
        String description = intent.getStringExtra("description");
        String isbn = intent.getStringExtra("isbn");

        titleTextView = findViewById(R.id.offerTitleCategoryISBN)
                .findViewById(R.id.offerTitleEditLayout)
                .findViewById(R.id.offerTitleEdit);
        TextView titleCharacters = findViewById(R.id.offerTitleCategoryISBN).findViewById(R.id.titleCharacters);

        titleTextView.addTextChangedListener(createTextWatcher(titleTextView, titleCharacters, 40));
        titleCharacters.setText("0/40");


        authorTextView = findViewById(R.id.offerTitleCategoryISBN)
                .findViewById(R.id.offerAuthorEditLayout)
                .findViewById(R.id.offerAuthorEdit);
        TextView authorCharacters = findViewById(R.id.offerTitleCategoryISBN).findViewById(R.id.authorCharacters);

        authorTextView.addTextChangedListener(createTextWatcher(authorTextView, authorCharacters, 40));
        authorCharacters.setText("0/40");

        isbnTextView = findViewById(R.id.offerTitleCategoryISBN)
                .findViewById(R.id.offerISBNEditLayout)
                .findViewById(R.id.offerISBNEdit);
        TextView isbnCharacters = findViewById(R.id.isbnCharacters);

        isbnTextView.addTextChangedListener(createTextWatcher(isbnTextView, isbnCharacters, 13));
        isbnCharacters.setText("0/13");

        descriptionTextView = findViewById(R.id.offerDescription)
                .findViewById(R.id.offerDescriptionLayout)
                .findViewById(R.id.offerDescriptionEdit);
        TextView descriptionCharacters = findViewById(R.id.descriptionCharacters);

        descriptionTextView.addTextChangedListener(createTextWatcher(descriptionTextView, descriptionCharacters, 300));
        descriptionCharacters.setText("0/300");

        categorySpinner = findViewById(R.id.offerTitleCategoryISBN).findViewById(R.id.offerCategoryLayout).findViewById(R.id.offerSpinner);

        categorySpinner.setAdapter(new ArrayAdapter<BookCategory>(this, android.R.layout.simple_spinner_dropdown_item, BookCategory.values()));

        titleTextView.setText(title);
        authorTextView.setText(author);
        descriptionTextView.setText(description);
        isbnTextView.setText(isbn);

        Button sendButton = findViewById(R.id.buttonSendOffer);
        sendButton.setText(R.string.confirmEdit);
        sendButton.setOnClickListener(l -> {
            Book bookEdit = new Book(titleTextView.getText().toString(),
                    authorTextView.getText().toString(),
                    isbnTextView.getText().toString(),
                    descriptionTextView.getText().toString(),
                    "/test/test",
                    (BookCategory) categorySpinner.getSelectedItem());
            Call<Book> call = RetrofitInstance.GetAPI().editBook("Bearer " + MainActivity.token, id, bookEdit);
            call.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {

                }
            });
        });

    }
    private TextWatcher createTextWatcher(EditText toWatch, TextView countDisplay, int limit){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                countDisplay.setText(toWatch.getText().length() + "/" + limit);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

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
}
