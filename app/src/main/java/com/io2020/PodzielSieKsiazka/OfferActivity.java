package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.offerActivityTitle);
    }

    public void sendOffer(View view){

        TextView titleTextView = findViewById(R.id.offerTitleCategoryISBN)
                .findViewById(R.id.offerTitleEditLayout)
                .findViewById(R.id.offerTitleEdit);
        String title = titleTextView.getText().toString();

        TextView isbnTextView = findViewById(R.id.offerTitleCategoryISBN)
                .findViewById(R.id.offerISBNEditLayout)
                .findViewById(R.id.offerISBNEdit);
        String isbn = isbnTextView.getText().toString();

        TextView descriptionTextView = findViewById(R.id.offerDescription)
                .findViewById(R.id.offerDescriptionLayout)
                .findViewById(R.id.offerDescriptionEdit);
        String description = descriptionTextView.getText().toString();

        Book book = new Book(0, title, "test", isbn, true, description, "/test/test.jpg","2020-12-12T16:07:14.855Z" , MainActivity.userID);
        Call<Book> call = RetrofitInstance.GetAPI().addBook(book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
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

}