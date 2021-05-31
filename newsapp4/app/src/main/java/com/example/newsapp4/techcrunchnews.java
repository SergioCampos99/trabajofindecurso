package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class techcrunchnews extends AppCompatActivity {
    AdapterB adapterB;
    Intent intencion;
    RecyclerView recyclerView;
    //public static String category = "business";
    public static String sources = "techcrunch";
    public static String API_KEY = "cdecf0c6a277499f81c7edf0bb044914";
    SwipeRefreshLayout srl;

    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techcrunchnews);

        srl = findViewById(R.id.swipeRefresh2);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(sources, API_KEY);
            }
        });


        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapterB = new AdapterB(techcrunchnews.this,articles);
        recyclerView.setAdapter(adapterB);
        retrieveJson(sources, API_KEY);

    }

    public void retrieveJson(String sources, String apiKey){

        srl.setRefreshing(true);
        Call<Headlines> call = ApiClientB.getInstance().getApi().getHeadlines(sources, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    srl.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapterB.setArticlesB(articles);
                }
            }


            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                srl.setRefreshing(false);
                Toast.makeText(techcrunchnews.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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