package com.example.myapplication.Retrofit;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RequestInterface {
    @POST("medisoft/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
