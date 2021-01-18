package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Book;
import com.io2020.PodzielSieKsiazka.schemas.BookCategory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivity extends AppCompatActivity {

    EditText titleTextView;
    EditText authorTextView;
    EditText isbnTextView;
    EditText descriptionTextView;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.offerActivityTitle);

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

    }

    public void sendOffer(View view){

        String title = titleTextView.getText().toString();
        String author = authorTextView.getText().toString();
        String isbn = isbnTextView.getText().toString();
        String description = descriptionTextView.getText().toString();
        BookCategory category = (BookCategory) categorySpinner.getSelectedItem();

        Book book = new Book(title, author, isbn, description, "/test/test.jpg", category);
        Call<Book> call = RetrofitInstance.GetAPI().addBook("Bearer " + MainActivity.token, book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private TextWatcher createTextWatcher(EditText toWatch, TextView countDisplay,  int limit){
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

}