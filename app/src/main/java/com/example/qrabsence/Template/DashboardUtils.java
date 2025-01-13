package com.example.qrabsence.Template;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.DTO.User;
import com.example.qrabsence.LoginActivity;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.StudentActivities.StudentDashActivity;
import com.example.qrabsence.StudentActivities.StudentProfileActivity;
import com.example.qrabsence.TeacherActivities.DashboardActivity;
import com.example.qrabsence.TeacherActivities.ProfileActivity;
import com.example.qrabsence.TeacherActivities.SessionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// this class contains the logic to fetch user data from api, and redirect to login in case of error
public class DashboardUtils {

    //fetch user data from api endpoint: /api/auth/user
    public static void fetchUserInfo(Context context, View mainContent, View fullPageLoader, OnUserFetchCallback callback) {
        MainApplication appContext = (MainApplication) context.getApplicationContext();

        if(appContext.user!=null){
            callback.onSuccess(appContext.user);
            handleSuccessUserFetch(context, mainContent, fullPageLoader, appContext.user);
            return;
        }


        String token = "Bearer " + appContext.getStoredToken();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> call = apiInterface.getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    appContext.user = response.body();
                    Log.i("DashboardUtils", "User data fetched successfully");
                    handleSuccessUserFetch(context, mainContent, fullPageLoader, appContext.user);
                    if (callback != null) {
                        callback.onSuccess(appContext.user);
                    }
                } else {
                    goToLogin(context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("DashboardUtils", "Error fetching user data: " + t.getMessage());
                goToLogin(context);
            }
        });
    }

    public static void fetchUserInfo(Context context, OnUserFetchCallback callback) {
        MainApplication appContext = (MainApplication) context.getApplicationContext();
        String token = "Bearer " + appContext.getStoredToken();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<User> call = apiInterface.getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    appContext.user = response.body();
                    Log.i("DashboardUtils", "User data fetched successfully");

                    if (callback != null) {
                        callback.onSuccess(appContext.user);
                    }
                } else {
                    goToLogin(context);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("DashboardUtils", "Error fetching user data: " + t.getMessage());
                goToLogin(context);
            }
        });
    }

    // Define a callback interface for handling user fetch success
    public interface OnUserFetchCallback {
        void onSuccess(User user);
    }

    //Redirect to login and kill current activity
    public static void goToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    //Hide Loader and Show Content
    private static void handleSuccessUserFetch(Context context, View mainContent, View fullPageLoader, User user) {
        mainContent.setVisibility(View.VISIBLE);
        fullPageLoader.setVisibility(View.INVISIBLE);
    }

    public static void setBottomNavBarLogic(
        BottomNavigationView bottomNavigationView,
        int pageId,
        Context context
    ){

        bottomNavigationView.setSelectedItemId(pageId);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home && pageId != itemId) {
                    Intent intent = new Intent(context, DashboardActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return true;
                } else if (itemId == R.id.nav_sessions && pageId != itemId) {
                    Intent intent = new Intent(context, SessionsActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return true;
                } else if (itemId == R.id.nav_profile && pageId != itemId) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return true;
                }

                return false;
            }
        });
    }

    public static void setBottomNavBarLogicStudent(
        BottomNavigationView bottomNavigationView,
        int pageId,
        Context context
    ){

        bottomNavigationView.setSelectedItemId(pageId);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home && pageId != itemId) {
                    Intent intent = new Intent(context, StudentDashActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return true;

                } else if (itemId == R.id.nav_profile && pageId != itemId) {
                    Intent intent = new Intent(context, StudentProfileActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                    return true;
                }

                return false;
            }
        });
    }

}
