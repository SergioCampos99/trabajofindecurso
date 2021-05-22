package com.example.newsapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.newsapp4.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    EditText emaillogin, passwordlogin;
    Button btnlogin, btnreg;
    VideoView videoBack;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //VideoBack
        videoBack = (VideoView) findViewById(R.id.vvBack);

        //EditText
        emaillogin = (EditText) findViewById(R.id.etEmail2);
        passwordlogin = (EditText) findViewById(R.id.etPass2);

        //Button
        btnlogin = (Button) findViewById(R.id.btn_inicioSesion2);
        btnreg = (Button) findViewById(R.id.btn_registro2);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginActivity.checkLogin().execute("");
            }
        });


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

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inicia la intencion hacia la actividad de registro si por ejemplo nos hemos equivocado
                Intent intencion = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intencion);
                finish();
            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            con = connectionClass(ConnectionClass.ip.toString(), ConnectionClass.port.toString(),ConnectionClass.db.toString(),ConnectionClass.un.toString(),ConnectionClass.pass.toString());
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Asegurate de estar conectado a internet", Toast.LENGTH_SHORT).show();
                    }
                });
                z = "On internet connection";

            }else{

            }
            try{
                String sql = "SELECT * FROM users WHERE email = '"+emaillogin.getText()+"' AND Pass = '"+passwordlogin.getText()+"' ";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Sesion iniciada, ¡bienvenido!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    z = "Success!";
                    Intent intencion = new Intent(LoginActivity.this, menuContent.class);
                    startActivity(intencion);
                    finish();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });

                    emaillogin.setText("");
                    passwordlogin.setText("");

                }
            }catch(Exception e){
                isSuccess = false;
                Log.e("SQL Error: ", e.getMessage());
            }
            return z;
        }
    }


    @SuppressLint("NewApi")
    public Connection connectionClass(String server, String port, String database, String username, String pass){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL= "jdbc:jtds:sqlserver://"+ server + ":" +port+ ";"+ "databaseName="+ database +";user="+username+";password="+pass+";";
            connection = DriverManager.getConnection(connectionURL);
            System.out.println(connectionURL);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
            System.out.println(connectionURL);
        }

        return connection;

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
}