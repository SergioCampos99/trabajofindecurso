package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class postsBarrio extends AppCompatActivity {

    ListView postList;
    ArrayList<String> Titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    Button btnpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_barrio);

        postList = (ListView) findViewById(R.id.lvPost);

        btnpost = (Button) findViewById(R.id.btnAPost);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion  = new Intent(postsBarrio.this, CrudPost.class);
                startActivity(intencion);
            }
        });
    }




}