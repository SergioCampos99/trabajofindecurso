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

public class sportsnews extends AppCompatActivity {
    Adapter adapter;
    Intent intencion;
    RecyclerView recyclerView;
    public static String API_KEY = "cdecf0c6a277499f81c7edf0bb044914";

    List<Articles> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportsnews);

        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String country = getCountry();
        retrieveJson(country, API_KEY);
    }

    public void retrieveJson(String country, String apiKey){

        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(sportsnews.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(sportsnews.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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