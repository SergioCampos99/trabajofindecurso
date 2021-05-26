package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menuContent extends AppCompatActivity {

    Intent intencion;
    Button btnZgz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_content);

        btnZgz = findViewById(R.id.btnBarrio);

        btnZgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent barrio = new Intent(menuContent.this, postsBarrio.class);
                startActivity(barrio);
            }
        });

    }
    public void aPerfil(View vista){
        intencion = new Intent(this, profile_activity.class);
        startActivity(intencion);
    }
    public void aDeportes(View vista){
        intencion = new Intent(this, bbcnews.class);
        startActivity(intencion);
    }
    public void aBusiness(View vista){
        intencion = new Intent(this, techcrunchnews.class);
        startActivity(intencion);
    }
    public void aApple(View vista){
        intencion = new Intent(this, AppleNews.class);
        startActivity(intencion);
    }
}