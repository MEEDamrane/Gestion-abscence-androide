package com.example.qrabsence;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.qrabsence.DTO.User;

public class MainApplication extends Application {
    public User user = null;
    private String token;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public void storeToken(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            Log.e(TAG, "Cannot store an empty or null token");
            return;
        }

        this.token = accessToken;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("api_token", accessToken);
        editor.apply();

        Log.i(TAG, "Token stored successfully");
    }

    public String getStoredToken() {
        if (this.token != null && !this.token.isEmpty()) {
            return this.token;
        }

        // Get stored API token
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String apiToken = sharedPreferences.getString("api_token", null);

        if (apiToken == null || apiToken.isEmpty()) {
            Log.w(TAG, "No valid token found in SharedPreferences");
            return null;
        }

        Log.i(TAG, "Stored Token Retrieved: " + apiToken);

        this.token = apiToken;
        return this.token;
    }

    public void dropToken(){
        this.token = null;
        this.user = null;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();  // Clears all stored data
        editor.apply();
    }
}
