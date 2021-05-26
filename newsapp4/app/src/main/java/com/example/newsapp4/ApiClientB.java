package com.example.newsapp4;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientB {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static ApiClientB apiClient;
    private static Retrofit retrofit;

    private ApiClientB(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized  ApiClientB getInstance(){
        if(apiClient == null){
            apiClient = new ApiClientB();
        }
        return apiClient;
    }

    public ApiInterfaceB getApi(){
        return retrofit.create(ApiInterfaceB.class);
    }
}
