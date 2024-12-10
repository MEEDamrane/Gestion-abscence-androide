package com.example.qrabsence;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        EditText emailOrCneInput = findViewById(R.id.EmailAddressInput);
        EditText passwordInput = findViewById(R.id.PasswordInput);
    }

    public void handleConnection(View button){
        EditText emailOrCneInput = findViewById(R.id.EmailAddressInput);
        EditText passwordInput = findViewById(R.id.PasswordInput);


    }
}
