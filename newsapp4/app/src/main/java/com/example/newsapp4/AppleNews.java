package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.newsapp4.Model.Articles;
import com.example.newsapp4.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppleNews extends AppCompatActivity {
    AdapterC adapterC;
    Intent intencion;
    RecyclerView recyclerView;
    //public static String category = "business";
    public static String q = "apple";
    public static String from = "2021-05-25";
    public static String to = "2021-05-25";
    public static String sortBy = "popularity";
    public static String API_KEY = "cdecf0c6a277499f81c7edf0bb044914";

    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applenews);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapterC = new AdapterC(AppleNews.this,articles);
        recyclerView.setAdapter(adapterC);
        retrieveJson(q, from, to, sortBy, API_KEY);

    }

    public void retrieveJson(String q, String from, String to, String sortBy, String apiKey){

        Call<Headlines> call = ApiClientC.getInstance().getApi().getHeadlines(q, from, to, sortBy, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    articles.clear();
                    articles = response.body().getArticles();
                    adapterC.setArticlesC(articles);
                }
            }


            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(AppleNews.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();

    }

    public void aPerfil(View vista){
        intencion = new Intent(this, profile_activity.class);
        startActivity(intencion);
    }
}