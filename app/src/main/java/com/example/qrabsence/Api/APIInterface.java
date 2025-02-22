package com.example.qrabsence.Api;

import com.example.qrabsence.DTO.LoginResponse;
import com.example.qrabsence.DTO.SessionRegisterResponse;
import com.example.qrabsence.DTO.User;
import com.example.qrabsence.DTO.SessionsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {


    @POST("/api/auth/login")
    Call<LoginResponse> login(
        @Query("email") String email,
        @Query("password") String password,
        @Query("remember_me") boolean rememberMe
    );

    @POST("/api/auth/logout")
    Call<Void> logout(@Header("Authorization") String authorization);

    @GET("/api/auth/user")
    Call<User> getUserInfo(@Header("Authorization") String authorization);

<<<<<<< HEAD
    @GET("api/session")
    Call<SessionsResponse> getSessions(@Header("Authorization") String token, @Query("page") int page, @Query("search") String search);
=======
    @POST("/api/session/register")
    Call<SessionRegisterResponse> registerSession(@Header("Authorization") String authorization,
                                                  @Query("id") Long id);
>>>>>>> 5c1da2348ad6239e9665e102f60cda56b2ba1a7b
}
