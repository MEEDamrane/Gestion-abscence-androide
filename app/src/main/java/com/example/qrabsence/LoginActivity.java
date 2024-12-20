package com.example.qrabsence;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.DTO.LoginResponse;
import com.example.qrabsence.TeacherActivities.DashboardActivity;

import retrofit2.Call;

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
        TextView errorText = findViewById(R.id.errorText);

        ProgressBar signinLoader = findViewById(R.id.signinLoader);

        // Show loader
        button.setEnabled(false);
        signinLoader.setVisibility(View.VISIBLE);

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<LoginResponse> call = apiInterface.login(
            emailOrCneInput.getText().toString().trim(),
            passwordInput.getText().toString(),
            true
        );

        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    errorText.setVisibility(View.GONE);
                    // Show loader
                    button.setEnabled(true); // Disable button
                    signinLoader.setVisibility(View.GONE);
                    Log.d("API_SUCCESS", "Login successful: " + loginResponse.toString());

                    handleSuccessfulConnection(loginResponse.getAccessToken());
                } else {
                    Log.d("API_ERROR", "Login failed: " + response.code());

                    if(response.code()!=401){
                        errorText.setText(R.string.loginFailedUnknownMessage);
                    }else{
                        errorText.setText(R.string.loginFailedMessage);
                    }

                    errorText.setVisibility(View.VISIBLE);


                    // Show loader
                    button.setEnabled(true); // Disable button
                    signinLoader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorText.setText(R.string.loginFailedUnknownMessage);
                errorText.setVisibility(View.VISIBLE);
                // Show loader
                button.setEnabled(true); // Disable button
                signinLoader.setVisibility(View.GONE);
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }


        });
    }

    private void handleSuccessfulConnection(String token){
        MainApplication context = (MainApplication) this.getApplicationContext();


        context.storeToken(token);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

        finish();
    }
}
