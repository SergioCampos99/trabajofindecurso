package com.example.newsapp4;

import com.example.newsapp4.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(@Query("sources")String source, @Query("apiKey")String apiKey);
}
