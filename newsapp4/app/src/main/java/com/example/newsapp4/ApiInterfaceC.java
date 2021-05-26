package com.example.newsapp4;

import com.example.newsapp4.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceC {

    @GET("everything")
    Call<Headlines> getHeadlines(@Query("q")String q, @Query("from")String from, @Query("to")String to, @Query("sortBy")String sortBy, @Query("apiKey")String apiKey);
}
