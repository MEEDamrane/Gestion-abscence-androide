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

import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.DTO.LoginResponse;
import com.example.qrabsence.DTO.SessionRegisterResponse;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.TeacherActivities.DashboardActivity;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

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
        View table = findViewById(R.id.displayTable);
        TextView view = findViewById(R.id.resultText);
        table.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this, QRCodeScannerActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("id");

            try {
                long value = Long.parseLong(result);

                registerRequest(value);

            } catch (NumberFormatException e) {

                Toast.makeText(this, "Le code QR scanné est au mauvais format", Toast.LENGTH_SHORT).show();
            }


        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Impossible de scanner le QR", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayText(String text, int colorResourceId) {
        TextView view = findViewById(R.id.resultText);
        View progressBar = findViewById(R.id.progressBar);

        // Resolve the color resource into a color value
        int color = ContextCompat.getColor(this, colorResourceId);

        view.setText(text);
        view.setTextColor(color);
        progressBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    private void displayTable(SessionRegisterResponse response){
        View table = findViewById(R.id.displayTable);

        TextView nomEnsaignant = findViewById(R.id.nomEnsaignant);
        TextView sessionIntitule = findViewById(R.id.sessionIntitule);
        TextView sessionDate = findViewById(R.id.sessionDate);

        table.setVisibility(View.VISIBLE);
        nomEnsaignant.setText(response.getEnseignant());
        sessionIntitule.setText(response.getIntitule());
        sessionDate.setText(response.getDate());


    }


    private void registerRequest(Long id){
        MainApplication appContext = (MainApplication) getApplicationContext();

        String token = "Bearer " + appContext.getStoredToken();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<SessionRegisterResponse> call = apiInterface.registerSession(token,id);

        View progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new retrofit2.Callback<SessionRegisterResponse>() {

            @Override
            public void onResponse(Call<SessionRegisterResponse> call, Response<SessionRegisterResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    SessionRegisterResponse sessionRegisterResponse = response.body();

                    displayText(sessionRegisterResponse.getMessage(), R.color.green);
                    displayTable(sessionRegisterResponse);

                    Log.d("API_SUCCESS", "message: " + sessionRegisterResponse);
                } else {
                    Log.d("API_ERROR", "register failed: " + response.code());

                    if (response.code() == 401) {
                        appContext.dropToken();
                        DashboardUtils.goToLogin(StudentDashActivity.this);
                    } else {
                        String errorMessage = "une erreur inattendue s'est produite, veuillez réessayer plus tard"; // Default message
                        try {
                            if (response.errorBody() != null) {
                                // Convert the error body to a string
                                String errorJson = response.errorBody().string();

                                // Parse the error JSON to extract the "message" field
                                JsonObject errorObject = new Gson().fromJson(errorJson, JsonObject.class);
                                if (errorObject.has("message")) {
                                    errorMessage = errorObject.get("message").getAsString();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("API_ERROR", "Failed to parse error response", e);
                        }

                        displayText(errorMessage, R.color.error);
                        Log.d("API_ERROR", "message: " + errorMessage);
                    }
                }
            }


            @Override
            public void onFailure(Call<SessionRegisterResponse> call, Throwable t) {

                displayText("une erreur inattendue s'est produite, veuillez réessayer plus tard",R.color.error);
                Log.e("API_FAILURE", "Error: " + t.getMessage());
            }
        });

    }
}
