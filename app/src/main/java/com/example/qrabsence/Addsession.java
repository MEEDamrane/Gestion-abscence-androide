package com.example.qrabsence;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Addsession extends AppCompatActivity {

    // Déclaration des variables
    private EditText etIntitule, etDate;
    private Button btnAddSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsession);

        // Liaison avec les éléments du fichier XML
        etIntitule = findViewById(R.id.etIntitule);
        etDate = findViewById(R.id.etDate);
        btnAddSession = findViewById(R.id.btnAddSession);

        // Listener pour le bouton
        btnAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération des valeurs saisies
                String intitule = etIntitule.getText().toString().trim();
                String date = etDate.getText().toString().trim();

                // Validation des champs
                if (intitule.isEmpty() || date.isEmpty()) {
                    Toast.makeText(Addsession.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    // Appel à la méthode pour créer une session
                    createSession(intitule, date);
                }
            }
        });
    }

    // Méthode pour créer une session via l'API
    private void createSession(String intitule, String date) {
        String url = "https://qr.smartgreenhouse.online/api/session";

        // Création de l'objet JSON
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("intitule", intitule);
            jsonBody.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la création du JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Envoi de la requête POST
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    // Succès de la requête
                    Toast.makeText(Addsession.this, "Session créée avec succès", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Erreur lors de la requête
                    Toast.makeText(Addsession.this, "Erreur : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        // Ajout de la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}
