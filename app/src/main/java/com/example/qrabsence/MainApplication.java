package com.example.qrabsence;

import android.app.Application;

import com.example.qrabsence.DTO.User;

public class MainApplication extends Application {
    public User user = null;
    public boolean is_signedIn = false;
    public String token;

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
