package com.example.qrabsence.TeacherActivities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SessionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

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

        DashboardUtils.setBottomNavBarLogic(bottomNavigationView,R.id.nav_sessions,this);

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        DashboardUtils.fetchUserInfo(this, mainContent, fullPageLoader, user -> {
            //instruction
        });
    }
}
