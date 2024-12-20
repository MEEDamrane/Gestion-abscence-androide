package com.example.qrabsence.Template;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TemplateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

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

        DashboardUtils.setBottomNavBarLogic(bottomNavigationView,R.id.nav_home,this);

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        DashboardUtils.fetchUserInfo(this, mainContent, fullPageLoader, user -> {
            //instruction
        });
    }
}
