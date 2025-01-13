package com.example.qrabsence.TeacherActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.EditorInfo;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.qrabsence.Adapters.SessionAdapter;
import com.example.qrabsence.Api.APIClient;
import com.example.qrabsence.Api.APIInterface;
import com.example.qrabsence.BaseActivity;
import com.example.qrabsence.DTO.Session;
import com.example.qrabsence.DTO.SessionsResponse;
import com.example.qrabsence.MainApplication;
import com.example.qrabsence.R;
import com.example.qrabsence.StudentActivities.StudentDashActivity;
import com.example.qrabsence.Template.DashboardUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionsActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private SessionAdapter adapter;
    private List<Session> sessionList = new ArrayList<>();
    private TextView currentPageText;
    private ImageButton nextPageButton;
    private ImageButton prevPageButton;
    private EditText searchEditText;
    private FloatingActionButton addSessionButton;
    private int currentPage = 1;
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

<<<<<<< HEAD
        // Initialize UI elements
        initializeViews();
        setupListeners();
        setupRecyclerView();

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);
        mainContent.setVisibility(View.INVISIBLE);
        fullPageLoader.setVisibility(View.VISIBLE);
=======
>>>>>>> 5c1da2348ad6239e9665e102f60cda56b2ba1a7b

        setupInsets(findViewById(R.id.main));
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.sessionsRecyclerView);
        currentPageText = findViewById(R.id.currentPageText);
        nextPageButton = findViewById(R.id.nextPageButton);
        prevPageButton = findViewById(R.id.prevPageButton);
        searchEditText = findViewById(R.id.searchEditText);
        addSessionButton = findViewById(R.id.addSessionButton);
    }

    private void setupListeners() {
        // Navigation buttons
        nextPageButton.setOnClickListener(v -> {
            currentPage++;
            fetchSessions();
        });

        prevPageButton.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                fetchSessions();
            }
        });

        // Search functionality
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchQuery = searchEditText.getText().toString();
                currentPage = 1; // Reset to first page when searching
                fetchSessions();
                return true;
            }
            return false;
        });

        // Add session button
        addSessionButton.setOnClickListener(v -> {
            // TODO: Implement add session functionality
            Toast.makeText(this, "Add session clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SessionAdapter(sessionList);
        recyclerView.setAdapter(adapter);
    }

    private void fetchSessions() {
        MainApplication context = (MainApplication) getApplicationContext();
        String token = "Bearer " + context.getStoredToken();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<SessionsResponse> call = apiInterface.getSessions(token, currentPage, searchQuery);

        call.enqueue(new Callback<SessionsResponse>() {
            @Override
            public void onResponse(Call<SessionsResponse> call, Response<SessionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SessionsResponse sessionsResponse = response.body();
                    sessionList.clear();
                    sessionList.addAll(sessionsResponse.getData());
                    adapter.notifyDataSetChanged();
                    
                    // Update pagination controls
                    currentPageText.setText("Page " + currentPage);
                    nextPageButton.setVisibility(
                        sessionsResponse.getNext_page_url() != null ? View.VISIBLE : View.GONE
                    );
                    prevPageButton.setVisibility(
                        sessionsResponse.getPrev_page_url() != null ? View.VISIBLE : View.GONE
                    );
                } else {
                    Toast.makeText(SessionsActivity.this, "Failed to fetch sessions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SessionsResponse> call, Throwable t) {
                Toast.makeText(SessionsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        MainApplication context = (MainApplication) getApplicationContext();

        View mainContent = findViewById(R.id.mainDashContent);
        View fullPageLoader = findViewById(R.id.fullScreenLoader);

        mainContent.setVisibility(View.INVISIBLE);
        fullPageLoader.setVisibility(View.VISIBLE);

        if(!context.getStoredIsEnseignant()){
            startActivity(new Intent(this, StudentDashActivity.class));
            finish();
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        DashboardUtils.setBottomNavBarLogic(bottomNavigationView, R.id.nav_sessions, this);

        DashboardUtils.fetchUserInfo(this, mainContent, fullPageLoader, user -> {
            fetchSessions();
        });
    }
}