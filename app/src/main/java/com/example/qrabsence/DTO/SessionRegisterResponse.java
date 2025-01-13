package com.example.qrabsence.DTO;

public class SessionRegisterResponse {

    private String message,intitule,date,enseignant;

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SessionRegisterResponse{" +
            "message='" + message + '\'' +
            ", intitule='" + intitule + '\'' +
            ", date='" + date + '\'' +
            ", enseignant='" + enseignant + '\'' +
            '}';
    }
}
