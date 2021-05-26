package com.example.newsapp4;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientC {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static ApiClientC apiClient;
    private static Retrofit retrofit;

    private ApiClientC(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized  ApiClientC getInstance(){
        if(apiClient == null){
            apiClient = new ApiClientC();
        }
        return apiClient;
    }

    public ApiInterfaceC getApi(){
        return retrofit.create(ApiInterfaceC.class);
    }
}
