package com.io2020.PodzielSieKsiazka;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitAPI;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.Book;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.io2020.PodzielSieKsiazka.schemas.AppUser;
import com.io2020.PodzielSieKsiazka.schemas.GoogleUserBody;
import com.io2020.PodzielSieKsiazka.schemas.LoginResponse;
import com.io2020.PodzielSieKsiazka.schemas.User;
import com.squareup.picasso.Picasso;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private View navigationHeader;
    private TextView nicknameField;
    private TextView emailField;
    private ImageView imageView;

    public static int userID;
    public static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitInstance.Create();
        AppUser appUser = (AppUser) getIntent().getSerializableExtra("AppUser");
        loginUser(appUser);
      
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(getApplicationContext(), OfferActivity.class);
                                       startActivity(intent);
                                   }
                               });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationHeader = navigationView.getHeaderView(0);
        nicknameField = navigationHeader.findViewById(R.id.nameView);
        emailField = navigationHeader.findViewById(R.id.emailView);
        imageView = navigationHeader.findViewById(R.id.imageView);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_slideshow) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });


        nicknameField.setText(appUser.getName());
        emailField.setText(appUser.getEmail());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Picasso.with(this)
                .load(appUser.getImgUrl())
                .resize(width / 5,width / 5)
                .transform(new RoundedTransformation(720, 0))
                .into(imageView);

        TextView logout = findViewById(R.id.nav_bottom)
                .findViewById(R.id.navbottom_linear)
                .findViewById(R.id.logout);
        logout.setOnClickListener(l -> {
            signOut();
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loginUser(AppUser user){
        Call<LoginResponse> call = RetrofitInstance.GetAPI().loginGoogleUser(new GoogleUserBody(user.getId(), user.getName()){
        });
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("failed to login: ", user.getId() + " " + user.getName());
                } else {
                    userID = response.body().getUser().get_id();
                    token = response.body().getToken();
                    Log.d("id: ", "" + userID);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }


    private void signOut(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, gso);
        client.signOut();
        Intent intent = new Intent(this, GoogleLogInActivity.class);
        startActivity(intent);
        finish();
    }


}