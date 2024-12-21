package com.example.qrabsence.StudentActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.TeacherActivities.DashboardActivity;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentDashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash);

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

        DashboardUtils.setBottomNavBarLogicStudent(bottomNavigationView,R.id.nav_home,this);

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

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

    public void handleClick(View button){
        Intent intent = new Intent(this, QRCodeScannerActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("id");
            Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Failed To Scan QR", Toast.LENGTH_SHORT).show();
        }
    }
}
