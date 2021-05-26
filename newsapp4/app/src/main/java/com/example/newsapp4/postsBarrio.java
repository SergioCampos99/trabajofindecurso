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

        crudPost crud = new crudPost();

        /*try{

            Statement stmt = crudPost.makePost().execute("");
            ResultSet rs = stmt.executeQuery("select * from posts");
            Titles.clear();
            final ArrayList<postmodel> pm = new ArrayList<postmodel>();
            while(rs.next()){
                postmodel obj = new postmodel();
                obj.title = rs.getString("postTitle");
                obj.description = rs.getString("postDesc");
                pm.add(obj);

                Titles.add("Title: " + obj.title + "\n Description: " + obj.description);
            }
            arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, Titles);
            postList.invalidateViews();




        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
*/
        btnpost = findViewById(R.id.btnAPost);
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(postsBarrio.this, crudPost.class);
                startActivity(intencion);
            }
        });

    }


}