package com.example.qrabsence.StudentActivities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        mainContent.setVisibility(View.INVISIBLE);
        fullPageLoader.setVisibility(View.VISIBLE);

        setupInsets(findViewById(R.id.main));
    }

    @Override
    protected void onStart() {
        super.onStart();
        MainApplication context = (MainApplication) getApplicationContext();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        DashboardUtils.setBottomNavBarLogicStudent(bottomNavigationView,R.id.nav_profile,this);

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        DashboardUtils.fetchUserInfo(this, mainContent, fullPageLoader, user -> {
            TextView userName = findViewById(R.id.userName);
            TextView userEmail = findViewById(R.id.userEmail);
            TextView userCNE = findViewById(R.id.userCNE);

            userName.setText(user.getNom() + " " + user.getPrenom());
            userEmail.setText(user.getEmail());
            userCNE.setText(user.getCNE()==null?"Not A Student":user.getCNE());
        });
    }

    public void handleDisconnection(View button){
        MainApplication context = (MainApplication) getApplicationContext();
        String token = "Bearer " + context.getStoredToken();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Void> call = apiInterface.logout(token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    handleSuccessLogout();

                    Log.d("LogoutResponse", "Successfully logged out");
                } else {
                    // Handle failure or error response
                    Log.e("LogoutError", "Logout failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure in making the request
                Log.e("LogoutError", "Request failed: " + t.getMessage());
            }
        });
    }

    private void handleSuccessLogout(){
        MainApplication context = (MainApplication) getApplicationContext();
        context.dropToken();
        DashboardUtils.goToLogin(this);
    }
}
