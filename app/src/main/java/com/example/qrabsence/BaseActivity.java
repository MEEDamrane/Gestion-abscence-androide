package com.example.qrabsence;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.Template.DashboardUtils;

public abstract class BaseActivity extends AppCompatActivity {

    protected void goToLogin() {
        DashboardUtils.goToLogin(this);
    }

    protected void setupInsets(View mainView) {
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
