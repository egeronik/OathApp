package com.example.oathapp.requesting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface userInterface {
    @GET("users.get?")
    Call<UserResponse> getById(@Query("user_ids") String user_id,
                               @Query("access_token") String access_token,
                               @Query("v") String v);
}
