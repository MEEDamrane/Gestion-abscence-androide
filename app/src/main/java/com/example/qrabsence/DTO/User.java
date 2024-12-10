package com.example.qrabsence.DTO;

public class User {
    private Long id;
    private String nom,prenom;
    private String email;
    private String CNE;
    private boolean is_enseignat;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter and Setter for prenom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for CNE
    public String getCNE() {
        return CNE;
    }

    public void setCNE(String CNE) {
        this.CNE = CNE;
    }

    // Getter and Setter for is_enseignat
    public boolean isIs_enseignat() {
        return is_enseignat;
    }

    public void setIs_enseignat(boolean is_enseignat) {
        this.is_enseignat = is_enseignat;
    }
}
