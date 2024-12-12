package com.example.qrabsence.DTO;

public class LoginResponse {
    private String accessToken; // Example field
    private String token_type; // Example field

    // Getters and setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
            "accessToken='" + accessToken + '\'' +
            ", token_type='" + token_type + '\'' +
            '}';
    }
}
