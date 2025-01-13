package com.example.qrabsence.TeacherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.StudentActivities.StudentDashActivity;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setupInsets(findViewById(R.id.main));



    }

    @Override
    protected void onStart() {
        super.onStart();
        MainApplication context = (MainApplication) getApplicationContext();

        if(!context.getStoredIsEnseignant()){
            startActivity(new Intent(this, StudentDashActivity.class));
            finish();
        }

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        mainContent.setVisibility(View.INVISIBLE);
        fullPageLoader.setVisibility(View.VISIBLE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        DashboardUtils.setBottomNavBarLogic(bottomNavigationView,R.id.nav_home,this);


        DashboardUtils.fetchUserInfo(this, mainContent, fullPageLoader, user -> {


            String firstPart = "Bonjour ";
            String userName = user.getPrenom();
            String fullText = firstPart + userName;

            SpannableString spannableString = new SpannableString(fullText);

            int nameStartIndex = firstPart.length();
            int nameColor = ContextCompat.getColor(this, R.color.lightGreen);
            ForegroundColorSpan nameColorSpan = new ForegroundColorSpan(nameColor);
            spannableString.setSpan(nameColorSpan, nameStartIndex, fullText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            TextView welcomingText = findViewById(R.id.welcomingTextField);
            welcomingText.setText(spannableString);
        });
    }


}
