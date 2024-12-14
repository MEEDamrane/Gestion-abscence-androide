package com.example.qrabsence.TeacherActivities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.DTO.User;
import com.example.qrabsence.LoginActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    Toast.makeText(DashboardActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_sessions) {
                    Toast.makeText(DashboardActivity.this, "Sessions Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    Toast.makeText(DashboardActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        MainApplication context = (MainApplication) this.getApplicationContext();
        String token = context.getStoredToken();

        if(token == null || token.isEmpty()){
            goToLogin();

        }else{
            View mainContent = findViewById(R.id.mainDashContent);
            View fullPageLoader = findViewById(R.id.fullScreenLoader);

            if(context.user == null){
                mainContent.setVisibility(View.INVISIBLE);
                fullPageLoader.setVisibility(View.VISIBLE);

                fetchUserInfo();

            }else{
                handleSuccessUserFetch();
            }
        }


    }

    //runs to get user data
    private void fetchUserInfo() {
        MainApplication context = (MainApplication) this.getApplicationContext();

        String token = "Bearer ";
        token+=context.getStoredToken();

        System.out.println("used token: "+token);

        // Create the API call
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> call = apiInterface.getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    context.user = response.body();

                    Log.i(TAG, "successful user data fetch: "+ context.user);
                    handleSuccessUserFetch();
                } else {
                    // Handle failure
                    //Toast.makeText(MainActivity.this, "Failed to get user info", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to get user info" );
                    goToLogin();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle error
                Log.e(TAG, "Error"+t.getMessage() );
                //Toast.makeText(this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                goToLogin();
            }

        });
    }

    //directs to login activity
    private void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //called after success in fetchUserInfo()
    private void handleSuccessUserFetch() {
        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);
        MainApplication context = (MainApplication) this.getApplicationContext();

        mainContent.setVisibility(View.VISIBLE);
        fullPageLoader.setVisibility(View.INVISIBLE);

        TextView welcomingText = findViewById(R.id.welcomingTextField);

        String firstPart = "Bonjour ";
        String userName = context.user.getPrenom();
        String fullText = firstPart + userName;

        // Create a SpannableString for the full text
        SpannableString spannableString = new SpannableString(fullText);

        // Set color for the name part
        int nameStartIndex = firstPart.length(); // Starting index for the name
        int nameEndIndex = fullText.length(); // End index for the name

        // Get the color from resources (use ContextCompat for compatibility)
        int nameColor = ContextCompat.getColor(this, R.color.lightGreen); // Correct way to get color

        // Apply color to the name
        ForegroundColorSpan nameColorSpan = new ForegroundColorSpan(nameColor);
        spannableString.setSpan(nameColorSpan, nameStartIndex, nameEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the spannable text to the TextView
        welcomingText.setText(spannableString);
    }
}
