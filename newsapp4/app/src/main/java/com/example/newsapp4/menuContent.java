package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menuContent extends AppCompatActivity {

    Intent intencion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_content);
    }
    public void aPerfil(View vista){
        intencion = new Intent(this, profile_activity.class);
        startActivity(intencion);
    }
    public void aDeportes(View vista){
        intencion = new Intent(this, sportsnews.class);
        startActivity(intencion);
    }
}