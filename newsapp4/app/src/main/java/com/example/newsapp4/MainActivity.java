package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button botonInicio, botonRegistro;
    VideoView videoBack;
    EditText pass, email, username;
    Intent intencion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoBack = (VideoView) findViewById(R.id.vvBack);
        botonRegistro = (Button) findViewById(R.id.btn_registro);
        email = (EditText) findViewById(R.id.etEmail);
        botonInicio = (Button) findViewById(R.id.btn_inicioSesion);

        String path ="android.resource://com.example.newsapp4/"+R.raw.mix;
        Uri u = Uri.parse(path);
        videoBack.setVideoURI(u);
        videoBack.start();

        videoBack.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        /*
        email.setVisibility(View.GONE);


        botonRegistro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                email.setVisibility(View.VISIBLE);
            }
        });

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setVisibility(View.GONE);
            }
        });
        */
    }
    protected void onResume(){
        videoBack.resume();
        super.onResume();
    }
    protected void onPause(){
        videoBack.suspend();
        super.onPause();
    }
    protected void onDestroy(){
        videoBack.stopPlayback();
        super.onDestroy();
    }

    public void PasarDatos(View vista){
        intencion = new Intent(this, menuContent.class);
        startActivity(intencion);
    }


}